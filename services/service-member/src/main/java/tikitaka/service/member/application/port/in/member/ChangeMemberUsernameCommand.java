package tikitaka.service.member.application.port.in.member;

import java.util.UUID;

public record ChangeMemberUsernameCommand(
		UUID id,
		String newUsername
) {
}
