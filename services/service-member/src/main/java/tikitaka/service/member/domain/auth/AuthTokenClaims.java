package tikitaka.service.member.domain.auth;

import java.time.Instant;
import java.util.UUID;

public record AuthTokenClaims(
		String tokenId,
		UUID accountId,
		AccountType accountType,
		TokenType tokenType,
		Instant expiresAt
) {
}
