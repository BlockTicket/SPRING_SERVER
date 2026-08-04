package tikitaka.service.auth.application.port.in;

public record RefreshCorporationCommand(
        String refreshToken
) {
}
