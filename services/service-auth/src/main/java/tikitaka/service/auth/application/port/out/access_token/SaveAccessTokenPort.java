package tikitaka.service.auth.application.port.out.access_token;

import tikitaka.service.auth.domain.role.Role;

import java.time.Duration;
import java.util.UUID;

public interface SaveAccessTokenPort {

	void save(
			UUID userId,
			Role role,
			String accessToken,
			Duration ttl
	);
}
