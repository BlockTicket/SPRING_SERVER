package tikitaka.service.member.adapter.in.web.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.member.application.port.in.auth.LoginCorporationCommand;

public record LoginCorporationRequest(

		@NotBlank
		String username,

		@NotBlank
		String password,

		boolean rememberMe

) {

	public LoginCorporationCommand toCommand() {

		return new LoginCorporationCommand(
				username,
				password,
				rememberMe
		);
	}
}