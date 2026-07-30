package tikitaka.service.member.adapter.in.web.data.request.corporation;

import tikitaka.service.member.application.port.in.corporation.ChangeCorporationUsernameCommand;

import java.util.UUID;

public record ChangeCorporationUsernameRequest(
		UUID id,
		String newUsername
) {

	public ChangeCorporationUsernameCommand toCommand() {

		return new ChangeCorporationUsernameCommand(
				id,
				newUsername
		);
	}
}
