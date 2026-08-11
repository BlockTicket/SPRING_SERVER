package tikitaka.service.auth.application.port.in.auth;

public interface RefreshTokenUseCase {

	String refresh(RefreshTokenCommand refreshTokenCommand);
}
