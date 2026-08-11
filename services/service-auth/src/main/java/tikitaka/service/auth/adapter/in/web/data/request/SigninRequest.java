package tikitaka.service.auth.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.auth.application.port.in.auth.SigninCommand;
import tikitaka.service.auth.domain.role.Role;

public record SigninRequest(
		@NotBlank
		String username,

		@NotBlank
		String password
) {

	public SigninCommand toCommand(Role role) {

		return new SigninCommand(
				username,
				password,
				role
		);
	}
}
