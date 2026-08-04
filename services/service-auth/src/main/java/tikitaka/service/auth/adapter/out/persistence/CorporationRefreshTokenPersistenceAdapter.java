package tikitaka.service.auth.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.out.LoadCorporationRefreshTokenPort;
import tikitaka.service.auth.application.port.out.SaveCorporationRefreshTokenPort;
import tikitaka.service.auth.domain.CorporationRefreshToken;
import tikitaka.service.auth.domain.exception.exception.InvalidRefreshTokenException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class CorporationRefreshTokenPersistenceAdapter implements
        SaveCorporationRefreshTokenPort,
        LoadCorporationRefreshTokenPort {

    private final CorporationRefreshTokenJpaRepository corporationRefreshTokenJpaRepository;

    @Override
    public void saveCorporationRefreshToken(
            CorporationRefreshToken corporationRefreshToken
    ) {

        corporationRefreshTokenJpaRepository.save(
                CorporationRefreshTokenJpaEntity.builder()
                        .corporationId(corporationRefreshToken.getCorporationId())
                        .refreshToken(corporationRefreshToken.getRefreshToken())
                        .expiredAt(corporationRefreshToken.getExpiredAt())
                        .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CorporationRefreshToken loadCorporationRefreshTokenByCorporationId(
            UUID corporationId
    ) {

        return corporationRefreshTokenJpaRepository.findById(corporationId)
                .map(entity -> new CorporationRefreshToken(
                        entity.getCorporationId(),
                        entity.getRefreshToken(),
                        entity.getExpiredAt()
                ))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CorporationRefreshToken loadCorporationRefreshToken(
            String refreshToken
    ) {

        CorporationRefreshTokenJpaEntity entity =
                corporationRefreshTokenJpaRepository.findByRefreshToken(refreshToken);

        if (entity == null) {
            throw new InvalidRefreshTokenException();
        }

        return new CorporationRefreshToken(
                entity.getCorporationId(),
                entity.getRefreshToken(),
                entity.getExpiredAt()
        );
    }
}
