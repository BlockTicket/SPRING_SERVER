package tikitaka.service.member.adapter.in.web.data.request.member;

import tikitaka.service.member.application.port.in.member.ChangeMemberPasswordCommand;

import java.util.UUID;

public record ChangeMemberPasswordRequest(
		UUID id,
		String currentPassword,
		String newPassword
) {

	public ChangeMemberPasswordCommand toCommand() {

		return new ChangeMemberPasswordCommand(
				id,
				currentPassword,
				newPassword
		);
	}
}
