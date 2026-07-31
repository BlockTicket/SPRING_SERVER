package tikitaka.service.member.application.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.member.ChangeMemberPasswordCommand;
import tikitaka.service.member.application.port.in.member.ChangeMemberPasswordUseCase;
import tikitaka.service.member.application.port.out.member.ChangeMemberPasswordPort;

@Service
@RequiredArgsConstructor
public class ChangeMemberPasswordService implements ChangeMemberPasswordUseCase {

	private final ChangeMemberPasswordPort changeMemberPasswordPort;

	@Override
	public void changePassword(
			ChangeMemberPasswordCommand changeMemberPasswordCommand
	) {

		changeMemberPasswordPort.changePassword(
				changeMemberPasswordCommand.id(),
				changeMemberPasswordCommand.currentPassword(),
				changeMemberPasswordCommand.newPassword()
		);
	}
}
