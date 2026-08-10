package tikitaka.service.auth.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.auth.application.port.in.member.SigninMemberCommand;

public record SigninMemberRequest(
		@NotBlank
		String username,

		@NotBlank
		String password
) {

	public SigninMemberCommand toCommand() {

		return new SigninMemberCommand(
				username,
				password
		);
	}
}
