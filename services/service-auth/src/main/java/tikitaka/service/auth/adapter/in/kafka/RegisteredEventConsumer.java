package tikitaka.service.auth.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tikitaka.core.kafka.Topics;
import tikitaka.core.kafka.event.CorporationRegisteredEvent;
import tikitaka.core.kafka.event.MemberRegisteredEvent;
import tikitaka.service.auth.application.port.in.credential.RegisterCredentialCommand;
import tikitaka.service.auth.application.port.in.credential.RegisterCredentialUseCase;
import tikitaka.service.auth.domain.role.Role;

@Component
@RequiredArgsConstructor
public class RegisteredEventConsumer {

	private final RegisterCredentialUseCase registerCredentialUseCase;

	@KafkaListener(topics = Topics.MEMBER_REGISTERED)
	public void onMemberRegistered(
			MemberRegisteredEvent memberRegisteredEvent
	) {

		registerCredentialUseCase.register(new RegisterCredentialCommand(
				memberRegisteredEvent.userId(),
				memberRegisteredEvent.username(),
				memberRegisteredEvent.password(),
				Role.MEMBER
		));
	}

	@KafkaListener(topics = Topics.CORPORATION_REGISTERED)
	public void onCorporationRegistered(
			CorporationRegisteredEvent corporationRegisteredEvent
	) {

		registerCredentialUseCase.register(new RegisterCredentialCommand(
				corporationRegisteredEvent.userId(),
				corporationRegisteredEvent.username(),
				corporationRegisteredEvent.password(),
				Role.CORPORATION
		));
	}
}
