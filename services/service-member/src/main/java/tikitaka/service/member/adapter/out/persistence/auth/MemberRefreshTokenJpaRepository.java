package tikitaka.service.member.adapter.out.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRefreshTokenJpaRepository extends JpaRepository<MemberRefreshTokenJpaEntity, UUID> {

	Optional<MemberRefreshTokenJpaEntity> findByMemberId(UUID memberId);

	void deleteByMemberId(UUID memberId);
}
