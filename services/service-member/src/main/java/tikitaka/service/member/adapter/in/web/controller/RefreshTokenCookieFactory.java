package tikitaka.service.member.adapter.in.web.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RefreshTokenCookieFactory {

	public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

	public ResponseCookie create(
			String refreshToken,
			long maxAgeSeconds
	) {

		return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
				.httpOnly(true)
				.path("/auth")
				.sameSite("Lax")
				.maxAge(Duration.ofSeconds(maxAgeSeconds))
				.build();
	}

	public ResponseCookie delete() {

		return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
				.httpOnly(true)
				.path("/auth")
				.sameSite("Lax")
				.maxAge(Duration.ZERO)
				.build();
	}
}
