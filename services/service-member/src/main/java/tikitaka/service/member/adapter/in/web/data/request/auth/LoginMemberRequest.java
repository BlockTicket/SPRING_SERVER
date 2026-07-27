package tikitaka.service.member.adapter.in.web.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.member.application.port.in.auth.LoginMemberCommand;

public record LoginMemberRequest(

		@NotBlank
		String username,

		@NotBlank
		String password,

		boolean rememberMe

) {

	public LoginMemberCommand toCommand() {

		return new LoginMemberCommand(
				username,
				password,
				rememberMe
		);
	}
}