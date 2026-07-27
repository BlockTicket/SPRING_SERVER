package tikitaka.service.member.application.port.out.auth;

import java.util.Optional;
import java.util.UUID;

public interface LoadMemberRefreshTokenPort {

	Optional<String> loadMemberRefreshTokenByMemberId(UUID memberId);
}
