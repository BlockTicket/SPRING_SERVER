package tikitaka.service.auth.application.port.in.auth;

import java.time.Duration;

public record SigninResult(
		String accessToken,
		String refreshToken,
		Duration refreshTokenTtl
) {
}
