package tikitaka.service.auth.application.port.in.member;

import java.time.Duration;

public record SigninMemberResult(
		String accessToken,
		String refreshToken,
		Duration refreshTokenTtl
) {
}
