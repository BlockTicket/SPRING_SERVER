package tikitaka.service.auth.application.port.out;

import tikitaka.service.auth.domain.MemberRefreshToken;

public interface SaveMemberRefreshTokenPort {

    void saveMemberRefreshToken(
            MemberRefreshToken memberRefreshToken
    );
}
