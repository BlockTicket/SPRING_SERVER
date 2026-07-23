package tikitaka.service.performance.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import tikitaka.service.performance.adapter.out.persistence.PerformanceJpaEntity;
import tikitaka.service.performance.domain.Performance;

@Component
public class PerformanceMapper {

    public PerformanceJpaEntity toEntity(Performance performance) {
        return PerformanceJpaEntity.builder()
                .title(performance.getTitle())
                .description(performance.getDescription())
                .location(performance.getLocation())
                .ageLimit(performance.getAgeLimit())
                .build();
    }

    public Performance toDomain(PerformanceJpaEntity entity) {
        return null;
    }
}