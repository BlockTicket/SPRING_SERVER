package tikitaka.service.member.application.port.out.member;

import java.util.UUID;

public interface ChangeMemberUsernamePort {

	void changeUsername(
			UUID id,
			String newUsername
	);
}
