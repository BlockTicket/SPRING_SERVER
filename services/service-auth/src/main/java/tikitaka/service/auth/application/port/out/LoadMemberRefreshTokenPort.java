package tikitaka.service.auth.application.port.out;

import tikitaka.service.auth.domain.MemberRefreshToken;

import java.util.UUID;

public interface LoadMemberRefreshTokenPort {

    MemberRefreshToken loadMemberRefreshTokenByMemberId(
            UUID memberId
    );

    MemberRefreshToken loadMemberRefreshToken(
            String refreshToken
    );
}
