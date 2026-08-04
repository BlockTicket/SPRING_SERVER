package tikitaka.service.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRefreshTokenJpaRepository
        extends JpaRepository<MemberRefreshTokenJpaEntity, UUID> {

    MemberRefreshTokenJpaEntity findByRefreshToken(
            String refreshToken
    );
}
