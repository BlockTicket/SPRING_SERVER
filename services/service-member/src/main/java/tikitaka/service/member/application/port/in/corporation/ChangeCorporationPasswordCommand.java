package tikitaka.service.member.application.port.in.corporation;

import java.util.UUID;

public record ChangeCorporationPasswordCommand(
		UUID id,
		String currentPassword,
		String newPassword
) {
}
