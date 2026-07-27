package tikitaka.service.member.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.member.application.port.in.corporation.ChangeCorporationUsernameCommand;
import tikitaka.service.member.application.port.in.corporation.ChangeCorporationUsernameUseCase;
import tikitaka.service.member.application.port.out.corporation.ChangeCorporationUsernamePort;

@Component
@RequiredArgsConstructor
public class ChangeCorporationUsernameService implements ChangeCorporationUsernameUseCase {

	private final ChangeCorporationUsernamePort changeCorporationUsernamePort;

	@Override
	public void changeCorporationUsername(
			ChangeCorporationUsernameCommand changeCorporationUsernameCommand
	) {

		changeCorporationUsernamePort.changeUsername(
				changeCorporationUsernameCommand.id(),
				changeCorporationUsernameCommand.newUsername()
		);
	}
}
