package tikitaka.service.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tikitaka.core.security.config.Pbkdf2HmacSHA512PasswordEncoder;
import tikitaka.service.member.adapter.in.web.filter.JwtAuthenticationFilter;
import tikitaka.service.member.adapter.in.web.handler.JwtAuthenticationEntryPoint;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

	private final String pepper;
	private final int iterations;

	public SecurityConfig(
			@Value("${PEPPER}") String pepper,
			@Value("${ITERATIONS}") int iterations
	) {

		this.pepper = pepper;
		this.iterations = iterations;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new Pbkdf2HmacSHA512PasswordEncoder(pepper, iterations);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity httpSecurity,
			JwtAuthenticationFilter jwtAuthenticationFilter,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
	) {

		httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> {})
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authorizeHttpRequests(auth ->
						auth
								.requestMatchers(
										HttpMethod.POST,
										"/api/member/register"
								).permitAll()

								.requestMatchers(
										HttpMethod.POST,
										"/api/corporation/register"
								).permitAll()

								.requestMatchers(
										HttpMethod.POST,
										"/api/nts/business/validate"
								).permitAll()

								.requestMatchers(
										HttpMethod.POST,
										"/api/auth/member/signin",
										"/api/auth/corporation/signin",
										"/api/auth/member/refresh",
										"/api/auth/corporation/refresh"
								).permitAll()

								.anyRequest().authenticated()
				)
				.exceptionHandling(exception ->
						exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				)
				.addFilterBefore(
						jwtAuthenticationFilter,
						UsernamePasswordAuthenticationFilter.class
				);

		return httpSecurity.build();
	}

	@Bean
	public CorsFilter corsFilter() {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		config.addExposedHeader("Refresh-Token");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
