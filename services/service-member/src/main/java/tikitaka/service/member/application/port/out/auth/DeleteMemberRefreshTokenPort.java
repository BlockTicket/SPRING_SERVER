package tikitaka.service.member.application.port.out.auth;

import java.util.UUID;

public interface DeleteMemberRefreshTokenPort {

	void deleteMemberRefreshTokenByMemberId(UUID memberId);
}
