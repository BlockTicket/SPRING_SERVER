package tikitaka.service.member.application.port.in.auth;

public record RefreshMemberCommand(
		String refreshToken
) {
}