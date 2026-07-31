package tikitaka.service.member.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.corporation.ChangeCorporationPasswordCommand;
import tikitaka.service.member.application.port.in.corporation.ChangeCorporationPasswordUseCase;
import tikitaka.service.member.application.port.out.corporation.ChangeCorporationPasswordPort;

@Service
@RequiredArgsConstructor
public class ChangeCorporationPasswordService implements ChangeCorporationPasswordUseCase {

	private final ChangeCorporationPasswordPort changeCorporationPasswordPort;

	@Override
	public void changePassword(
			ChangeCorporationPasswordCommand changeCorporationPasswordCommand
	) {

		changeCorporationPasswordPort.changePassword(
				changeCorporationPasswordCommand.id(),
				changeCorporationPasswordCommand.currentPassword(),
				changeCorporationPasswordCommand.newPassword()
		);
	}
}
