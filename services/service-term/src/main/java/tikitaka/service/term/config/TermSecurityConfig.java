package tikitaka.service.term.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import tikitaka.core.common.data.CommonResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class TermSecurityConfig {

	@Bean
	public SecurityFilterChain termSecurityFilterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
		http
				// 쿠키/세션 로그인을 사용하지 않는 API입니다. 인증 연동 전에는 쓰기 요청이 차단됩니다.
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.logout(AbstractHttpConfigurer::disable)
				.requestCache(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
						.requestMatchers(HttpMethod.GET, "/api/terms", "/api/term/*").permitAll()
						.requestMatchers(HttpMethod.HEAD, "/api/terms", "/api/term/*").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/term").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/api/term").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/term/*").hasRole("ADMIN")
						.anyRequest().denyAll())
				.exceptionHandling(exceptions -> exceptions
						.authenticationEntryPoint((request, response, exception) -> writeError(
								objectMapper, response, HttpStatus.UNAUTHORIZED,
								"인증이 필요합니다.", "TERM_AUTHENTICATION_REQUIRED"))
						.accessDeniedHandler((request, response, exception) -> writeError(
								objectMapper, response, HttpStatus.FORBIDDEN,
								"약관을 변경할 권한이 없습니다.", "TERM_ACCESS_DENIED")));

		return http.build();
	}

	private void writeError(ObjectMapper objectMapper, HttpServletResponse response,
			HttpStatus status, String message, String code) throws IOException {
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		objectMapper.writeValue(response.getOutputStream(), CommonResponse.of(status, message, code, null));
	}
}
