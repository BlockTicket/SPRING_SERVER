package tikitaka.service.member.adapter.out.persistence.external.nts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NtsBusinessJpaRepository extends JpaRepository<NtsBusinessJpaEntity, UUID> {
}
