package tikitaka.service.auth.application.port.in.corporation;

import java.time.Duration;

public record SigninCorporationResult(
		String accessToken,
		String refreshToken,
		Duration refreshTokenTtl
) {
}
