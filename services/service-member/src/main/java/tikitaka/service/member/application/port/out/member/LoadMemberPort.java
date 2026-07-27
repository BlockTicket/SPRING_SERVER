package tikitaka.service.member.application.port.out.member;

import tikitaka.service.member.domain.member.Member;

public interface LoadMemberPort {

	Member loadMemberByUsername(String username);
}
