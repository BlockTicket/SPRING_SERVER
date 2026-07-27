package tikitaka.service.member.application.port.in.auth;

public interface LoginCorporationUseCase {

	LoginCorporationResult loginCorporation(
			LoginCorporationCommand loginCorporationCommand
	);
}