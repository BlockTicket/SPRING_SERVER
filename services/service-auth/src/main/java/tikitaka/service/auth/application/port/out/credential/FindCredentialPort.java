package tikitaka.service.auth.application.port.out.credential;

import tikitaka.service.auth.domain.credential.Credential;
import tikitaka.service.auth.domain.role.Role;

import java.util.Optional;

public interface FindCredentialPort {

	Optional<Credential> findByUsernameAndRole(
			String username,
			Role role
	);
}
