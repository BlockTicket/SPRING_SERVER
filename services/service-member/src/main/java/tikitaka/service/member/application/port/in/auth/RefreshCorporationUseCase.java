package tikitaka.service.member.application.port.in.auth;

public interface RefreshCorporationUseCase {

	LoginCorporationResult refresh(
			RefreshCorporationCommand refreshCorporationCommand
	);
}