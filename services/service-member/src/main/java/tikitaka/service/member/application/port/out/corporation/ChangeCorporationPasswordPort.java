package tikitaka.service.member.application.port.out.corporation;

import java.util.UUID;

public interface ChangeCorporationPasswordPort {

	void changePassword(
			UUID id,
			String currentPassword,
			String newPassword
	);
}