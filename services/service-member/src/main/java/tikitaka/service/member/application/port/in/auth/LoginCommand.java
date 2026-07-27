package tikitaka.service.member.application.port.in.auth;

import tikitaka.service.member.domain.auth.AccountType;

public record LoginCommand(
		String username,
		String password,
		AccountType accountType
) {
}
