package tikitaka.service.member.application.port.in.auth;

public record MemberRefreshTokenCommand(
		String refreshToken
) {
}
