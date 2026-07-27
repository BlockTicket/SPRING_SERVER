package tikitaka.service.member.application.port.in.auth;

public record LoginCorporationCommand(
        String username,
        String password,
        boolean rememberMe
) {
}