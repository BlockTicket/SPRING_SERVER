package tikitaka.service.auth.application.port.in.auth;

public interface SigninUseCase {

	SigninResult signin(SigninCommand signinCommand);
}
