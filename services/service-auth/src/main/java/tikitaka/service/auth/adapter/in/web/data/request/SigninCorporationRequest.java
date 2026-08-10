package tikitaka.service.auth.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.auth.application.port.in.corporation.SigninCorporationCommand;

public record SigninCorporationRequest(
		@NotBlank
		String username,

		@NotBlank
		String password
) {

	public SigninCorporationCommand toCommand() {

		return new SigninCorporationCommand(
				username,
				password
		);
	}
}
