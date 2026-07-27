package tikitaka.service.member.adapter.out.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidAccessTokenJpaRepository extends JpaRepository<InvalidAccessTokenJpaEntity, String> {
}
