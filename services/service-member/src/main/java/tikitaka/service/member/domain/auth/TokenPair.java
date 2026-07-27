package tikitaka.service.member.domain.auth;

public record TokenPair(
		String accessToken,
		String refreshToken,
		long refreshTokenExpirationSeconds
) {
}
