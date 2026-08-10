package tikitaka.service.member.application.port.out.event;

import java.util.UUID;

public interface PublishMemberRegisteredEventPort {

	void publishMemberRegistered(
			UUID userId,
			String username,
			String passwordHash
	);
}
