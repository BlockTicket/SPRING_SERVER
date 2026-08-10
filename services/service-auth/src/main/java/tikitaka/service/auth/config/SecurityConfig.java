package tikitaka.service.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tikitaka.core.security.config.Pbkdf2HmacSHA512PasswordEncoder;

@Configuration
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
			HttpSecurity httpSecurity
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
										"/api/auth/member/signin",
										"/api/auth/member/signout",
										"/api/auth/member/refresh",
										"/api/auth/corporation/signin",
										"/api/auth/corporation/signout",
										"/api/auth/corporation/refresh"
								).permitAll()

								.requestMatchers(
										HttpMethod.GET,
										"/health"
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
