package tikitaka.service.member.application.port.out;

import tikitaka.service.member.domain.member.Member;

public interface SaveMemberPort {

	void saveMember(Member member);
}
