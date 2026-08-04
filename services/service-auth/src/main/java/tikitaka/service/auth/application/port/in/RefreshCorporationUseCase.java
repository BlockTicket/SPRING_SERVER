package tikitaka.service.auth.application.port.in;

public interface RefreshCorporationUseCase {

    LoginCorporationResult refresh(
            RefreshCorporationCommand refreshCorporationCommand
    );
}
