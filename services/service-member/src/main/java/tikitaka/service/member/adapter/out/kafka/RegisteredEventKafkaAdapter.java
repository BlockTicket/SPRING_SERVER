package tikitaka.service.member.adapter.out.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tikitaka.core.kafka.Topics;
import tikitaka.core.kafka.event.CorporationRegisteredEvent;
import tikitaka.core.kafka.event.MemberRegisteredEvent;
import tikitaka.service.member.application.port.out.event.PublishCorporationRegisteredEventPort;
import tikitaka.service.member.application.port.out.event.PublishMemberRegisteredEventPort;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegisteredEventKafkaAdapter implements
		PublishMemberRegisteredEventPort,
		PublishCorporationRegisteredEventPort {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void publishMemberRegistered(
			UUID userId,
			String username,
			String passwordHash
	) {

		kafkaTemplate.send(
				Topics.MEMBER_REGISTERED,
				userId.toString(),
				new MemberRegisteredEvent(userId, username, passwordHash)
		);
	}

	@Override
	public void publishCorporationRegistered(
			UUID userId,
			String username,
			String passwordHash
	) {

		kafkaTemplate.send(
				Topics.CORPORATION_REGISTERED,
				userId.toString(),
				new CorporationRegisteredEvent(userId, username, passwordHash)
		);
	}
}
