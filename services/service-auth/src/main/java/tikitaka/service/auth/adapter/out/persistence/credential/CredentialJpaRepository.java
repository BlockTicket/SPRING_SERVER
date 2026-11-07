package tikitaka.service.auth.adapter.out.persistence.credential;

import org.springframework.data.jpa.repository.JpaRepository;
import tikitaka.service.auth.domain.role.Role;

import java.util.Optional;
import java.util.UUID;

public interface CredentialJpaRepository extends JpaRepository<CredentialJpaEntity, UUID> {

	Optional<CredentialJpaEntity> findByUsernameAndRole(
			String username,
			Role role
	);
}
