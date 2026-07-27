package tikitaka.service.member.application.port.in.auth;

public record LoginMemberResult(
		String accessToken,
		String refreshToken
) {
}