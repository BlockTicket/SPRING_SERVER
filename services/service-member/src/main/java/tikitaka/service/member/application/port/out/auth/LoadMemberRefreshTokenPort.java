package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.MemberRefreshToken;

import java.util.UUID;

public interface LoadMemberRefreshTokenPort {

    MemberRefreshToken loadMemberRefreshTokenByMemberId(
            UUID memberId
    );


    MemberRefreshToken loadMemberRefreshToken(
            String refreshToken
    );
}