package tikitaka.service.term.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TermJpaRepository extends JpaRepository<TermJpaEntity, UUID> {
}
