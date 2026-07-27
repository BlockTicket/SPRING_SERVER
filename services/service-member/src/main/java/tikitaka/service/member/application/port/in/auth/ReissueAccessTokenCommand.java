package tikitaka.service.member.application.port.in.auth;

public record ReissueAccessTokenCommand(
		String refreshToken
) {
}
