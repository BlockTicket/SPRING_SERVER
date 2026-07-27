package tikitaka.service.member.adapter.in.web.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.member.application.port.in.auth.LoginCommand;

public record LoginRequest(
		@NotBlank
		String username,

		@NotBlank
		String password
) {

	public LoginCommand toCommand() {

		return new LoginCommand(
				username,
				password
		);
	}
}
