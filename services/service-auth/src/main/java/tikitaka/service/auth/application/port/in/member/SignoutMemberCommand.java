package tikitaka.service.auth.application.port.in.member;

public record SignoutMemberCommand(
		String accessToken
) {
}
