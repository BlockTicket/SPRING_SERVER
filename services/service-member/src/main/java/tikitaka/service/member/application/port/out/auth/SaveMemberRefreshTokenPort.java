package tikitaka.service.member.application.port.out.auth;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SaveMemberRefreshTokenPort {

	void saveMemberRefreshToken(
			UUID memberId,
			String refreshToken,
			LocalDateTime expiredAt
	);
}
