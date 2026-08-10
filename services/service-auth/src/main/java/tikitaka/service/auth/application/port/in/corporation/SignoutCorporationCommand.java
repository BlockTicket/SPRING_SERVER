package tikitaka.service.auth.application.port.in.corporation;

public record SignoutCorporationCommand(
		String accessToken
) {
}
