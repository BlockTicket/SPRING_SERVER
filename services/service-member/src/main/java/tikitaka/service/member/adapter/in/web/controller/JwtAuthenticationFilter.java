package tikitaka.service.member.adapter.in.web.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.application.port.out.auth.InvalidAccessTokenPort;
import tikitaka.service.member.application.port.out.auth.JwtTokenPort;
import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.TokenType;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtTokenPort jwtTokenPort;
	private final InvalidAccessTokenPort invalidAccessTokenPort;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		String authorization = request.getHeader(AUTHORIZATION_HEADER);

		if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {

			try {

				AuthTokenClaims authTokenClaims = jwtTokenPort.parse(
						authorization.substring(BEARER_PREFIX.length()),
						TokenType.ACCESS
				);

				if (!invalidAccessTokenPort.isInvalidated(authTokenClaims.tokenId())) {

					SecurityContextHolder.getContext().setAuthentication(
							new UsernamePasswordAuthenticationToken(authTokenClaims, null, List.of())
					);
				}
			} catch (CommonException ignored) {

				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}
}
