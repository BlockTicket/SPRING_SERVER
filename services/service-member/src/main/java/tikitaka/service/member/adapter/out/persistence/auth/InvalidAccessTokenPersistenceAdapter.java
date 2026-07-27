package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.auth.InvalidAccessTokenPort;
import tikitaka.service.member.domain.auth.InvalidAccessToken;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class InvalidAccessTokenPersistenceAdapter implements InvalidAccessTokenPort {

	private final InvalidAccessTokenJpaRepository invalidAccessTokenJpaRepository;

	@Override
	public void save(InvalidAccessToken invalidAccessToken) {

		invalidAccessTokenJpaRepository.save(InvalidAccessTokenJpaEntity.builder()
				.tokenId(invalidAccessToken.tokenId())
				.expiresAt(invalidAccessToken.expiresAt())
				.build()
		);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isInvalidated(String tokenId) {

		return invalidAccessTokenJpaRepository.findById(tokenId)
				.map(invalidAccessTokenJpaEntity -> invalidAccessTokenJpaEntity.getExpiresAt().isAfter(Instant.now()))
				.orElse(false);
	}
}
