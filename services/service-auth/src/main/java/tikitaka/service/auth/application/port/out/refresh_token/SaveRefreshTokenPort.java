package tikitaka.service.auth.application.port.out.refresh_token;

import tikitaka.service.auth.domain.refresh_token.RefreshToken;

public interface SaveRefreshTokenPort {

	void save(RefreshToken refreshToken);
}
