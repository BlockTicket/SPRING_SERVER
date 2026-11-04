package tikitaka.service.auth.application.port.out.jwt;

import tikitaka.service.auth.domain.role.Role;

import java.util.UUID;

public record TokenPayload(
		UUID tokenId,
		UUID userId,
		Role role
) {
}
