package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.member.Member;

public interface LoadMemberPort {

    Member loadMemberByUsername(
            String username
    );
}