package tikitaka.service.auth.application.port.in.credential;

import tikitaka.service.auth.domain.role.Role;

import java.util.UUID;

public record RegisterCredentialCommand(
		UUID id,
		String username,
		String passwordHash,
		Role role
) {
}
