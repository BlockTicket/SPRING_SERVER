package tikitaka.service.member.application.port.out.auth;

import java.util.UUID;

public interface DeleteCorporationRefreshTokenPort {

	void deleteCorporationRefreshTokenByCorporationId(UUID corporationId);
}
