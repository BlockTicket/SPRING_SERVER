package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.auth.RefreshTokenPort;
import tikitaka.service.member.domain.auth.AccountType;
import tikitaka.service.member.domain.auth.RefreshToken;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RefreshTokenPersistenceAdapter implements RefreshTokenPort {

	private final RefreshTokenJpaRepository refreshTokenJpaRepository;

	@Override
	public void save(RefreshToken refreshToken) {

		refreshTokenJpaRepository.save(RefreshTokenJpaEntity.builder()
				.tokenId(refreshToken.tokenId())
				.accountId(refreshToken.accountId())
				.accountType(refreshToken.accountType())
				.expiresAt(refreshToken.expiresAt())
				.build()
		);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<RefreshToken> findByTokenId(String tokenId) {

		return refreshTokenJpaRepository.findById(tokenId)
				.map(this::toRefreshToken);
	}

	@Override
	public void deleteByTokenId(String tokenId) {

		refreshTokenJpaRepository.deleteById(tokenId);
	}

	@Override
	public void deleteByAccountIdAndAccountType(
			UUID accountId,
			AccountType accountType
	) {

		refreshTokenJpaRepository.deleteByAccountIdAndAccountType(accountId, accountType);
	}

	private RefreshToken toRefreshToken(RefreshTokenJpaEntity refreshTokenJpaEntity) {

		return new RefreshToken(
				refreshTokenJpaEntity.getTokenId(),
				refreshTokenJpaEntity.getAccountId(),
				refreshTokenJpaEntity.getAccountType(),
				refreshTokenJpaEntity.getExpiresAt()
		);
	}
}
