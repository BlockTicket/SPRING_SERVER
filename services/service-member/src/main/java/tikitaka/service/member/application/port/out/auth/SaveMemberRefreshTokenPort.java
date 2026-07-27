package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.MemberRefreshToken;

public interface SaveMemberRefreshTokenPort {

	void saveMemberRefreshToken(
			MemberRefreshToken memberRefreshToken
	);
}