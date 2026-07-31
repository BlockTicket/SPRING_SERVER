package tikitaka.service.member.application.port.in.member;

import java.util.UUID;

public record ChangeMemberPasswordCommand(
		UUID id,
		String currentPassword,
		String newPassword
) {
}
