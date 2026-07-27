package tikitaka.service.member.adapter.in.web.data.request.member;

import tikitaka.service.member.application.port.in.member.ChangeMemberUsernameCommand;

import java.util.UUID;

public record ChangeMemberUsernameRequest(
		UUID id,

		String newUsername
) {

	public ChangeMemberUsernameCommand toCommand() {

		return new ChangeMemberUsernameCommand(
				id,
				newUsername
		);
	}
}
