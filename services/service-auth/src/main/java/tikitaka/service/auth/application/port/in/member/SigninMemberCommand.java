package tikitaka.service.auth.application.port.in.member;

public record SigninMemberCommand(
		String username,
		String password
) {
}
