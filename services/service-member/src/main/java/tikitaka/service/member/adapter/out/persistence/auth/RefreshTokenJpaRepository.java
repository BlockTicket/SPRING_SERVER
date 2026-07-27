package tikitaka.service.member.adapter.out.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import tikitaka.service.member.domain.auth.AccountType;

import java.util.UUID;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, String> {

	void deleteByAccountIdAndAccountType(
			UUID accountId,
			AccountType accountType
	);
}
