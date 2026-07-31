package tikitaka.service.member.adapter.in.web.data.request.corporation;

import tikitaka.service.member.application.port.in.corporation.ChangeCorporationPasswordCommand;

import java.util.UUID;

public record ChangeCorporationPasswordRequest(
		UUID id,
		String currentPassword,
		String newPassword
) {

	public ChangeCorporationPasswordCommand toCommand() {

		return new ChangeCorporationPasswordCommand(
				id,
				currentPassword,
				newPassword
		);
	}
}
