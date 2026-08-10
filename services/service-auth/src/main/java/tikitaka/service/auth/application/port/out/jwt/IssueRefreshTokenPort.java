package tikitaka.service.auth.application.port.out.jwt;

import tikitaka.service.auth.domain.role.Role;

import java.time.Duration;
import java.util.UUID;

public interface IssueRefreshTokenPort {

	IssuedRefreshToken issueRefreshToken(
			UUID userId,
			Role role
	);

	record IssuedRefreshToken(
			String token,
			Duration ttl
	) {}
}
