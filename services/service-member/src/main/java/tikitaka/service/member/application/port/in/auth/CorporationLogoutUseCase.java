package tikitaka.service.member.application.port.in.auth;

public interface CorporationLogoutUseCase {

	void corporationLogout(CorporationRefreshTokenCommand corporationRefreshTokenCommand);
}
