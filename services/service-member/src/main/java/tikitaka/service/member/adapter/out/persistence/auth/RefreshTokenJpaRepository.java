package tikitaka.service.member.adapter.out.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, String> {

	@Modifying
	@Query("""
			DELETE FROM RefreshTokenJpaEntity refreshToken
			WHERE refreshToken.accountId = :accountId
			AND refreshToken.accountType = tikitaka.service.member.domain.auth.AccountType.MEMBER
			""")
	void deleteMemberByAccountId(@Param("accountId") UUID accountId);

	@Modifying
	@Query("""
			DELETE FROM RefreshTokenJpaEntity refreshToken
			WHERE refreshToken.accountId = :accountId
			AND refreshToken.accountType = tikitaka.service.member.domain.auth.AccountType.CORPORATION
			""")
	void deleteCorporationByAccountId(@Param("accountId") UUID accountId);
}
