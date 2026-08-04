package tikitaka.service.auth.application.port.in;

public record LoginCorporationResult(
        String accessToken,
        String refreshToken
) {
}
