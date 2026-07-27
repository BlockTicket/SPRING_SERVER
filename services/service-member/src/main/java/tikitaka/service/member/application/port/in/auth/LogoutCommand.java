package tikitaka.service.member.application.port.in.auth;

import tikitaka.service.member.domain.auth.AccountType;

public record LogoutCommand(
		String accessToken,
		String refreshToken,
		AccountType accountType
) {
}
