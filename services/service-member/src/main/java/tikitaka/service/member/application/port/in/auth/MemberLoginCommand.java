package tikitaka.service.member.application.port.in.auth;

public record MemberLoginCommand(
		String username,
		String password
) {
}
