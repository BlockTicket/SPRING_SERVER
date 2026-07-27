package tikitaka.service.member.adapter.in.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tikitaka.service.member.application.port.out.auth.JwtPort;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


	private final JwtPort jwtPort;


	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {


		String authorization =
				request.getHeader("Authorization");


		if (authorization == null ||
				!authorization.startsWith("Bearer ")) {

			filterChain.doFilter(
					request,
					response
			);

			return;
		}


		String token =
				authorization.substring(7);


		try {

			if (!jwtPort.validateToken(token)) {

				filterChain.doFilter(
						request,
						response
				);

				return;
			}


			UUID id =
					jwtPort.getId(token);


			String type =
					jwtPort.getType(token);


			MemberAuthentication authentication =
					new MemberAuthentication(
							id,
							type
					);


			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(
							authentication,
							null,
							Collections.emptyList()
					);


			SecurityContextHolder
					.getContext()
					.setAuthentication(
							authenticationToken
					);


		} catch (JwtException | IllegalArgumentException e) {

			SecurityContextHolder.clearContext();
		}


		filterChain.doFilter(
				request,
				response
		);
	}
}