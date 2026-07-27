package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenPort {

	void save(RefreshToken refreshToken);

	Optional<RefreshToken> findByTokenId(String tokenId);

	void deleteByTokenId(String tokenId);

	void deleteMemberByAccountId(UUID accountId);

	void deleteCorporationByAccountId(UUID accountId);
}
