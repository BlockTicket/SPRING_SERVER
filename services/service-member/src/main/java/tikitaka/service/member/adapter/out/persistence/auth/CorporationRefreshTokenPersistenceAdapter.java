package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.auth.LoadCorporationRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.SaveCorporationRefreshTokenPort;
import tikitaka.service.member.domain.auth.CorporationRefreshToken;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
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

        CorporationRefreshTokenJpaEntity entity =
                corporationRefreshTokenJpaRepository.findById(corporationId)
                        .orElseThrow(
                                InvalidRefreshTokenException::new
                        );


        return new CorporationRefreshToken(
                entity.getCorporationId(),
                entity.getRefreshToken(),
                entity.getExpiredAt()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public CorporationRefreshToken loadCorporationRefreshToken(
            String refreshToken
    ) {

        CorporationRefreshTokenJpaEntity entity =
                corporationRefreshTokenJpaRepository.findByRefreshToken(
                        refreshToken
                );


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