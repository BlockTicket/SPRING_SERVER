package tikitaka.service.member.application.port.in.auth;

public record LoginMemberCommand(
        String username,
        String password,
        boolean rememberMe
) {
}