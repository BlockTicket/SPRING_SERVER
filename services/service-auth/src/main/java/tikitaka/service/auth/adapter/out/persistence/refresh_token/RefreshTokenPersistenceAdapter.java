package tikitaka.service.auth.adapter.out.persistence.refresh_token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.out.refresh_token.DeleteRefreshTokenPort;
import tikitaka.service.auth.application.port.out.refresh_token.FindRefreshTokenPort;
import tikitaka.service.auth.application.port.out.refresh_token.SaveRefreshTokenPort;
import tikitaka.service.auth.domain.refresh_token.RefreshToken;
import tikitaka.service.auth.domain.role.Role;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RefreshTokenPersistenceAdapter implements
		FindRefreshTokenPort,
		DeleteRefreshTokenPort,
		SaveRefreshTokenPort {

	private final RefreshTokenJpaRepository refreshTokenJpaRepository;

	@Override
	public Optional<RefreshToken> findByUserIdAndRole(
			UUID userId,
			Role role
	) {

		return refreshTokenJpaRepository
				.findByUserIdAndRole(userId, role)
				.map(entity -> new RefreshToken(
						entity.getUserId(),
						entity.getRole(),
						entity.getToken(),
						entity.getExpiresAt()
				));
	}

	@Override
	public void deleteByUserIdAndRole(
			UUID userId,
			Role role
	) {

		refreshTokenJpaRepository.deleteByUserIdAndRole(userId, role);
	}

	@Override
	public void save(
			RefreshToken refreshToken
	) {

		refreshTokenJpaRepository.save(RefreshTokenJpaEntity.builder()
				.userId(refreshToken.getUserId())
				.role(refreshToken.getRole())
				.token(refreshToken.getToken())
				.expiresAt(refreshToken.getExpiresAt())
				.build()
		);
	}
}
