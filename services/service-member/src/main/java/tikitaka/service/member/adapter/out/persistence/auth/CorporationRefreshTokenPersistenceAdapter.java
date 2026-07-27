package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.auth.DeleteCorporationRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.LoadCorporationRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.SaveCorporationRefreshTokenPort;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationRefreshTokenPersistenceAdapter implements
		SaveCorporationRefreshTokenPort,
		LoadCorporationRefreshTokenPort,
		DeleteCorporationRefreshTokenPort {

	private final CorporationRefreshTokenJpaRepository corporationRefreshTokenJpaRepository;

	@Override
	public void saveCorporationRefreshToken(
			UUID corporationId,
			String refreshToken,
			LocalDateTime expiredAt
	) {

		CorporationRefreshTokenJpaEntity corporationRefreshTokenJpaEntity = corporationRefreshTokenJpaRepository
				.findByCorporationId(corporationId)
				.orElse(null);

		if (corporationRefreshTokenJpaEntity != null) {

			corporationRefreshTokenJpaEntity.updateRefreshToken(
					refreshToken,
					expiredAt
			);

			return;
		}

		corporationRefreshTokenJpaRepository.save(
				CorporationRefreshTokenJpaEntity.builder()
						.corporationId(corporationId)
						.refreshToken(refreshToken)
						.expiredAt(expiredAt)
						.build()
		);
	}

	@Override
	public Optional<String> loadCorporationRefreshTokenByCorporationId(UUID corporationId) {

		return corporationRefreshTokenJpaRepository.findByCorporationId(corporationId)
				.map(CorporationRefreshTokenJpaEntity::getRefreshToken);
	}

	@Override
	public void deleteCorporationRefreshTokenByCorporationId(UUID corporationId) {

		corporationRefreshTokenJpaRepository.deleteByCorporationId(corporationId);
	}
}
