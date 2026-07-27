package tikitaka.service.member.application.port.out.auth;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SaveCorporationRefreshTokenPort {

	void saveCorporationRefreshToken(
			UUID corporationId,
			String refreshToken,
			LocalDateTime expiredAt
	);
}
