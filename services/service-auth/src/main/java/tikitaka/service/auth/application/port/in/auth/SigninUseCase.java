package tikitaka.service.auth.application.port.in.auth;

public interface SigninUseCase {

	SignInResult signin(SignInCommand signinCommand);
}
