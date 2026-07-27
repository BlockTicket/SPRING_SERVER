package tikitaka.service.member.application.port.out.auth;

import java.util.Optional;
import java.util.UUID;

public interface LoadCorporationRefreshTokenPort {

	Optional<String> loadCorporationRefreshTokenByCorporationId(UUID corporationId);
}
