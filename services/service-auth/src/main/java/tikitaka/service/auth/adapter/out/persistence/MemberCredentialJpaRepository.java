package tikitaka.service.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberCredentialJpaRepository
        extends JpaRepository<MemberCredentialJpaEntity, UUID> {

    Optional<MemberCredentialJpaEntity> findByUsername(
            String username
    );
}
