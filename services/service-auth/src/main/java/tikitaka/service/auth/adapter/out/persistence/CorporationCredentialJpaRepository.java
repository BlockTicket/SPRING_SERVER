package tikitaka.service.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CorporationCredentialJpaRepository
        extends JpaRepository<CorporationCredentialJpaEntity, UUID> {

    Optional<CorporationCredentialJpaEntity> findByUsername(
            String username
    );
}
