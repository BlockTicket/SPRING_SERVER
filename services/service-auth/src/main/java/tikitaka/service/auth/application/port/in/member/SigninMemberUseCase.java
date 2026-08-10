package tikitaka.service.auth.application.port.in.member;

public interface SigninMemberUseCase {

	SigninMemberResult signin(SigninMemberCommand signinMemberCommand);
}
