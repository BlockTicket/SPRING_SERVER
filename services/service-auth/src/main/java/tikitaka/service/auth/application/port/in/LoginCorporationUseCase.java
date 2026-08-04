package tikitaka.service.auth.application.port.in;

public interface LoginCorporationUseCase {

    LoginCorporationResult loginCorporation(
            LoginCorporationCommand loginCorporationCommand
    );
}
