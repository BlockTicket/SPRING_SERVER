package tikitaka.service.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.RegisterMemberCommand;
import tikitaka.service.member.application.port.in.RegisterMemberUseCase;
import tikitaka.service.member.application.port.out.SaveMemberPort;
import tikitaka.service.member.domain.member.Member;

@Service
@RequiredArgsConstructor
public class MemberCommandService implements RegisterMemberUseCase {

	private final SaveMemberPort saveMemberPort;

	@Override
	public void registerMember(
			RegisterMemberCommand registerMemberCommand
	) {

		saveMemberPort.saveMember(new Member(
				registerMemberCommand.id(),
				registerMemberCommand.username(),
				registerMemberCommand.email(),
				registerMemberCommand.phone(),
				registerMemberCommand.password(),
				registerMemberCommand.userType(),
				registerMemberCommand.provider()
		));
	}
}