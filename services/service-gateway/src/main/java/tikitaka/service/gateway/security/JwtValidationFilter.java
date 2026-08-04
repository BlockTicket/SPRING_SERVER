package tikitaka.service.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

    public static final String MEMBER_ID_HEADER = "X-Member-Id";
    public static final String MEMBER_TYPE_HEADER = "X-Member-Type";

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/**",
            "/member/api/member/register",
            "/member/api/corporation/register",
            "/member/api/nts/business/validate",
            "/member/swagger-ui/**",
            "/member/v3/api-docs/**",
            "/file/swagger-ui/**",
            "/file/v3/api-docs/**",
            "/actuator/health",
            "/actuator/info"
    );

    private final JwtProperties jwtProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private SecretKey getKey() {

        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        HttpServletRequest sanitized = removeIdentityHeaders(request);

        if (isPublicPath(request.getRequestURI())) {

            filterChain.doFilter(sanitized, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            writeUnauthorized(response, "인증 토큰이 없습니다.");
            return;
        }

        String token = authorization.substring(7);

        Claims claims;

        try {

            claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {

            writeUnauthorized(response, "유효하지 않은 Access Token 입니다.");
            return;
        }

        HttpServletRequest enriched = addIdentityHeaders(
                sanitized,
                claims.getSubject(),
                claims.get("type", String.class)
        );

        filterChain.doFilter(enriched, response);
    }

    private boolean isPublicPath(
            String uri
    ) {

        return PUBLIC_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }

    private void writeUnauthorized(
            HttpServletResponse response,
            String message
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                "{\"status\":401,\"code\":\"INVALID_ACCESS_TOKEN\",\"message\":\"" + message + "\",\"data\":null}"
        );
    }

    private HttpServletRequest removeIdentityHeaders(
            HttpServletRequest request
    ) {

        return new HttpServletRequestWrapper(request) {

            @Override
            public String getHeader(String name) {

                if (MEMBER_ID_HEADER.equalsIgnoreCase(name)
                        || MEMBER_TYPE_HEADER.equalsIgnoreCase(name)) {
                    return null;
                }

                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {

                if (MEMBER_ID_HEADER.equalsIgnoreCase(name)
                        || MEMBER_TYPE_HEADER.equalsIgnoreCase(name)) {
                    return Collections.emptyEnumeration();
                }

                return super.getHeaders(name);
            }
        };
    }

    private HttpServletRequest addIdentityHeaders(
            HttpServletRequest request,
            String memberId,
            String memberType
    ) {

        Map<String, String> extraHeaders = Map.of(
                MEMBER_ID_HEADER, memberId,
                MEMBER_TYPE_HEADER, memberType == null ? "" : memberType
        );

        return new HttpServletRequestWrapper(request) {

            @Override
            public String getHeader(String name) {

                for (Map.Entry<String, String> entry : extraHeaders.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase(name)) {
                        return entry.getValue();
                    }
                }

                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {

                for (Map.Entry<String, String> entry : extraHeaders.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase(name)) {
                        return Collections.enumeration(List.of(entry.getValue()));
                    }
                }

                return super.getHeaders(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {

                List<String> names = Collections.list(super.getHeaderNames());
                names.addAll(extraHeaders.keySet());

                return Collections.enumeration(names);
            }
        };
    }
}
