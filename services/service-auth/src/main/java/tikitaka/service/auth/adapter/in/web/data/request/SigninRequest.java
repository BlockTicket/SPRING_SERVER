package tikitaka.service.auth.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.auth.application.port.in.auth.SignInCommand;
import tikitaka.service.auth.domain.role.Role;

public record SigninRequest(
		@NotBlank
		String username,

		@NotBlank
		String password
) {

	public SignInCommand toCommand(Role role) {

		return new SignInCommand(
				username,
				password,
				role
		);
	}
}
