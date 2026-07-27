package tikitaka.service.member.adapter.in.web.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final String INVALID_ACCESS_TOKEN_RESPONSE = """
			{"httpStatus":401,"message":"유효하지 않은 Access Token입니다.","code":"INVALID_ACCESS_TOKEN"}
			""";

	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException
	) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(INVALID_ACCESS_TOKEN_RESPONSE);
	}
}
