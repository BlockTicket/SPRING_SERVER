package tikitaka.service.member.application.port.in.corporation;

import java.util.UUID;

public record ChangeCorporationUsernameCommand(
		UUID id,
		String newUsername
) {
}
