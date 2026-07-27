package tikitaka.service.member.application.port.in.auth;

public record LoginCommand(
		String username,
		String password
) {
}
