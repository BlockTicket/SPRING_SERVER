package tikitaka.service.performance.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<PerformanceJpaEntity, UUID> {
}
