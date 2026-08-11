package tikitaka.service.auth.application.port.in.auth;

public interface SignoutUseCase {

	void signout(SignoutCommand signoutCommand);
}
