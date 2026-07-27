package tikitaka.service.member.application.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.member.ChangeMemberUsernameCommand;
import tikitaka.service.member.application.port.in.member.ChangeMemberUsernameUseCase;
import tikitaka.service.member.application.port.out.member.ChangeMemberUsernamePort;

@Service
@RequiredArgsConstructor
public class ChangeMemberUsernameService implements ChangeMemberUsernameUseCase {

	private final ChangeMemberUsernamePort changeMemberUsernamePort;

	@Override
	public void changeUsername(
			ChangeMemberUsernameCommand changeMemberUsernameCommand
	) {

		changeMemberUsernamePort.changeUsername(
				changeMemberUsernameCommand.id(),
				changeMemberUsernameCommand.username()
		);
	}
}
