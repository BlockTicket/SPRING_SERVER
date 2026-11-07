package tikitaka.service.auth.application.port.out.jwt;

import tikitaka.service.auth.domain.role.Role;

import java.time.Duration;
import java.util.UUID;

public interface IssueAccessTokenPort {

	IssuedAccessToken issueAccessToken(
			UUID userId,
			Role role
	);

	record IssuedAccessToken(
			String token,
			Duration ttl
	) {}
}
