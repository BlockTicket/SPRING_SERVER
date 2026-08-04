package tikitaka.service.auth.application.port.in;

public record LoginMemberResult(
        String accessToken,
        String refreshToken
) {
}
