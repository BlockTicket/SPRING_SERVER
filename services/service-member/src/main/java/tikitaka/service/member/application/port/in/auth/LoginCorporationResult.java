package tikitaka.service.member.application.port.in.auth;

public record LoginCorporationResult(
		String accessToken,
		String refreshToken
) {
}