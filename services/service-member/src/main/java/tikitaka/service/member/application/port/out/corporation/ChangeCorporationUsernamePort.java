package tikitaka.service.member.application.port.out.corporation;

import java.util.UUID;

public interface ChangeCorporationUsernamePort {

	void changeUsername(
			UUID id,
			String newUsername
	);
}
