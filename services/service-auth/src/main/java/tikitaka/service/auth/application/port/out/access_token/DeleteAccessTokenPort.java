package tikitaka.service.auth.application.port.out.access_token;

import tikitaka.service.auth.domain.role.Role;

import java.util.UUID;

public interface DeleteAccessTokenPort {

	void deleteByUserIdAndRole(
			UUID userId,
			Role role
	);
}
