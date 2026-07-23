package tikitaka.service.performance.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.performance.adapter.out.persistence.mapper.PerformanceMapper;
import tikitaka.service.performance.application.port.out.PerformancePort;
import tikitaka.service.performance.domain.Performance;

@Component
@RequiredArgsConstructor
public class PerformancePersistenceAdapter implements PerformancePort {

    private final PerformanceRepository performanceRepository;
    private final PerformanceMapper performanceMapper;

    @Override
    public Performance save(Performance performance) {
        performanceRepository.save(performanceMapper.toEntity(performance));
        return performance;
    }
}