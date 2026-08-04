package tikitaka.service.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CorporationRefreshTokenJpaRepository
        extends JpaRepository<CorporationRefreshTokenJpaEntity, UUID> {

    CorporationRefreshTokenJpaEntity findByRefreshToken(
            String refreshToken
    );
}
