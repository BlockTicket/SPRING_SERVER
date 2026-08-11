package tikitaka.service.member.adapter.out.persistence.corporation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CorporationJpaRepository extends JpaRepository<CorporationJpaEntity, UUID> {
}
