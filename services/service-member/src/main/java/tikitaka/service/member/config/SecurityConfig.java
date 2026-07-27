package tikitaka.service.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import tikitaka.service.member.adapter.in.web.controller.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final String pepper;
	private final int iterations;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(
			@Value("${PEPPER}") String pepper,
			@Value("${ITERATIONS}") int iterations,
			JwtAuthenticationFilter jwtAuthenticationFilter
	) {

		this.pepper = pepper;
		this.iterations = iterations;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new Pbkdf2HmacSHA512PasswordEncoder(pepper, iterations);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity httpSecurity
	) {

		httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> {})
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(auth ->
						auth
								.requestMatchers(
										HttpMethod.GET,
										"/swagger-ui/**",
										"/v3/api-docs/**"
								).permitAll()

								.requestMatchers(
										HttpMethod.POST,
										"/api/member/register",
										"/api/auth/member/login",
										"/api/auth/member/logout",
										"/api/auth/member/refresh"
								).permitAll()

								.requestMatchers(
										HttpMethod.POST,
										"/api/corporation/register",
										"/api/auth/corporation/login",
										"/api/auth/corporation/logout",
										"/api/auth/corporation/refresh"
								).permitAll()

								.requestMatchers(
										HttpMethod.POST,
										"/api/nts/business/validate"
								).permitAll()

								.anyRequest().authenticated()
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

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
