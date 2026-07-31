package tikitaka.service.member.application.port.out.member;

import java.util.UUID;

public interface ChangeMemberPasswordPort {

	void changePassword(
			UUID id,
			String currentPassword,
			String newPassword
	);
}
