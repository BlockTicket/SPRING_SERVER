package tikitaka.service.auth.adapter.in.web.cookie;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public final class RefreshTokenCookieFactory {

	private static final String COOKIE_NAME = "refreshToken";
	private static final String COOKIE_PATH = "/";

	private RefreshTokenCookieFactory() {}

	public static ResponseCookie create(
			String refreshToken,
			Duration ttl
	) {

		return ResponseCookie.from(COOKIE_NAME, refreshToken)
				.httpOnly(true)
				.secure(true)
				.sameSite("Strict")
				.path(COOKIE_PATH)
				.maxAge(ttl)
				.build();
	}
}
