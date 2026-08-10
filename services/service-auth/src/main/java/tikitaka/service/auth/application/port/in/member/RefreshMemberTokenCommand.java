package tikitaka.service.auth.application.port.in.member;

public record RefreshMemberTokenCommand(
		String refreshToken
) {
}
