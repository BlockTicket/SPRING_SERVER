package tikitaka.service.auth.application.port.in.auth;

import tikitaka.service.auth.domain.role.Role;

public record SigninCommand(
		String username,
		String password,
		Role role
) {
}
