package tikitaka.service.auth.application.port.in.corporation;

public record RefreshCorporationTokenCommand(
		String refreshToken
) {
}
