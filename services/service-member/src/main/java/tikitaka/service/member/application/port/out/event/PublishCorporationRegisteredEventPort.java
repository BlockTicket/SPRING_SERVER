package tikitaka.service.member.application.port.out.event;

import java.util.UUID;

public interface PublishCorporationRegisteredEventPort {

	void publishCorporationRegistered(
			UUID userId,
			String username,
			String passwordHash
	);
}
