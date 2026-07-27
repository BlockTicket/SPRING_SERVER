package tikitaka.service.member.domain.auth;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(
		String tokenId,
		UUID accountId,
		AccountType accountType,
		Instant expiresAt
) {
}
