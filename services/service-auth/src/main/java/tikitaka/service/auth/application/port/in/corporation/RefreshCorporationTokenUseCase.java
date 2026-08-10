package tikitaka.service.auth.application.port.in.corporation;

public interface RefreshCorporationTokenUseCase {

	String refresh(RefreshCorporationTokenCommand refreshCorporationTokenCommand);
}
