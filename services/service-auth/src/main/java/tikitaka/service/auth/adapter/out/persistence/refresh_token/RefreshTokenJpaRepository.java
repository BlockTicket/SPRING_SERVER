package tikitaka.service.auth.adapter.out.persistence.refresh_token;

import org.springframework.data.jpa.repository.JpaRepository;
import tikitaka.service.auth.domain.role.Role;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, RefreshTokenId> {

	Optional<RefreshTokenJpaEntity> findByUserIdAndRole(
			UUID userId,
			Role role
	);

	void deleteByUserIdAndRole(
			UUID userId,
			Role role
	);
}
