package tikitaka.service.member.adapter.out.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CorporationRefreshTokenJpaRepository
        extends JpaRepository<CorporationRefreshTokenJpaEntity, UUID> {


    CorporationRefreshTokenJpaEntity findByRefreshToken(
            String refreshToken
    );
}