package tikitaka.service.member.adapter.in.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tikitaka.service.member.adapter.in.web.handler.JwtAuthenticationEntryPoint;
import tikitaka.service.member.adapter.in.web.security.TokenHeaders;
import tikitaka.service.member.application.port.out.auth.JwtPort;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Set<String> PUBLIC_AUTH_PATHS = Set.of(
			"/api/auth/member/signin",
			"/api/auth/corporation/signin",
			"/api/auth/member/refresh",
			"/api/auth/corporation/refresh"
	);

	private final JwtPort jwtPort;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		String requestPath = request.getRequestURI()
				.substring(request.getContextPath().length());

		return "POST".equals(request.getMethod())
				&& PUBLIC_AUTH_PATHS.contains(requestPath);
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || authorizationHeader.isBlank()) {

			filterChain.doFilter(request, response);
			return;
		}

		if (!TokenHeaders.hasBearerPrefix(authorizationHeader)) {

			jwtAuthenticationEntryPoint.commence(request, response, null);
			return;
		}

		String accessToken = TokenHeaders.withoutBearerPrefix(authorizationHeader);

		if (accessToken.isBlank() || !jwtPort.validateAccessToken(accessToken)) {

			jwtAuthenticationEntryPoint.commence(request, response, null);
			return;
		}

		UUID memberId = jwtPort.getMemberId(accessToken);
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
						memberId,
						null,
						AuthorityUtils.NO_AUTHORITIES
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}
}
