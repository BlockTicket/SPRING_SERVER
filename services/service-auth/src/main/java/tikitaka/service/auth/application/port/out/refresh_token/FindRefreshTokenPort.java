package tikitaka.service.auth.application.port.out.refresh_token;

import tikitaka.service.auth.domain.refresh_token.RefreshToken;
import tikitaka.service.auth.domain.role.Role;

import java.util.Optional;
import java.util.UUID;

public interface FindRefreshTokenPort {

	Optional<RefreshToken> findByUserIdAndRole(
			UUID userId,
			Role role
	);
}
