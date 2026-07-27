package tikitaka.service.member.application.port.in.auth;

public record CorporationLoginCommand(
		String username,
		String password
) {
}
