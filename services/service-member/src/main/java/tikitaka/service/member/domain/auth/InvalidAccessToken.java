package tikitaka.service.member.domain.auth;

import java.time.Instant;

public record InvalidAccessToken(
		String tokenId,
		Instant expiresAt
) {
}
