package tikitaka.service.auth.application.port.in.auth;

public interface SignInUseCase {

	SignInResult signin(SignInCommand signinCommand);
}
