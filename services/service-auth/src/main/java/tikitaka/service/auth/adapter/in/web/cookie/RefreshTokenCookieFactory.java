package tikitaka.service.auth.adapter.in.web.cookie;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public final class RefreshTokenCookieFactory {

	private RefreshTokenCookieFactory() {}

	public static ResponseCookie create(
			String refreshToken,
			Duration ttl
	) {

		return ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)
				.sameSite("Strict")
				.path("/")
				.maxAge(ttl)
				.build();
	}
}
