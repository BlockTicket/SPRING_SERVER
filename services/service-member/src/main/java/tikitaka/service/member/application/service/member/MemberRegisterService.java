package tikitaka.service.member.application.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.member.RegisterMemberCommand;
import tikitaka.service.member.application.port.in.member.RegisterMemberUseCase;
import tikitaka.service.member.application.port.out.event.PublishMemberRegisteredEventPort;
import tikitaka.service.member.application.port.out.member.SaveMemberPort;
import tikitaka.service.member.domain.member.Member;

@Service
@RequiredArgsConstructor
public class MemberRegisterService implements RegisterMemberUseCase {

	private final SaveMemberPort saveMemberPort;
	private final PublishMemberRegisteredEventPort publishMemberRegisteredEventPort;

	@Override
	public void registerMember(
			RegisterMemberCommand registerMemberCommand
	) {

		String passwordHash = saveMemberPort.saveMember(new Member(
				registerMemberCommand.id(),
				registerMemberCommand.username(),
				registerMemberCommand.email(),
				registerMemberCommand.phone(),
				registerMemberCommand.password(),
				registerMemberCommand.provider()
		));

		publishMemberRegisteredEventPort.publishMemberRegistered(
				registerMemberCommand.id(),
				registerMemberCommand.username(),
				passwordHash
		);
	}
}