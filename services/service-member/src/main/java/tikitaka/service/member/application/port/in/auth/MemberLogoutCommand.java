package tikitaka.service.member.application.port.in.auth;

public record MemberLogoutCommand(
		String accessToken
) {
}
