package tikitaka.service.auth.application.port.in;

public record LoginCorporationCommand(
        String username,
        String password,
        boolean rememberMe
) {
}
