package tikitaka.service.auth.application.port.in;

public record LoginMemberCommand(
        String username,
        String password,
        boolean rememberMe
) {
}
