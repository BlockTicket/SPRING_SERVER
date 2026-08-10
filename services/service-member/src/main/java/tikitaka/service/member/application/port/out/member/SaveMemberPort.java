package tikitaka.service.member.application.port.out.member;

import tikitaka.service.member.domain.member.Member;

public interface SaveMemberPort {

	String saveMember(Member member);
}
