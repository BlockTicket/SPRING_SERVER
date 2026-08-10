package tikitaka.service.auth.application.port.in.corporation;

public record SigninCorporationCommand(
		String username,
		String password
) {
}
