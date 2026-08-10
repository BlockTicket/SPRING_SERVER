package tikitaka.service.auth.application.port.in.corporation;

public interface SigninCorporationUseCase {

	SigninCorporationResult signin(SigninCorporationCommand signinCorporationCommand);
}
