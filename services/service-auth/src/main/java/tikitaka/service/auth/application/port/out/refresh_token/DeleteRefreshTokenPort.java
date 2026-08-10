package tikitaka.service.auth.application.port.out.refresh_token;

import tikitaka.service.auth.domain.role.Role;

import java.util.UUID;

public interface DeleteRefreshTokenPort {

	void deleteByUserIdAndRole(
			UUID userId,
			Role role
	);
}
