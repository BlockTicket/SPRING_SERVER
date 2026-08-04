package tikitaka.service.auth.application.port.in;

public record RefreshMemberCommand(
        String refreshToken
) {
}
