package tikitaka.service.auth.application.port.in.auth;

import tikitaka.service.auth.domain.role.Role;

public record SignOutCommand(
		String accessToken,
		Role role
) {
}
