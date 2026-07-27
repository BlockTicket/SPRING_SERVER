package tikitaka.service.member.domain.auth;

import java.util.UUID;

public record AuthenticationAccount(
		UUID id,
		String username,
		String password,
		AccountType accountType
) {
}
