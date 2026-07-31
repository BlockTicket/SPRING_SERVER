package tikitaka.service.performance.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.performance.application.port.in.PerformanceUseCase;
import tikitaka.service.performance.application.port.in.data.CreatePerformanceCommand;
import tikitaka.service.performance.application.port.out.PerformancePort;
import tikitaka.service.performance.domain.Performance;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PerformanceService implements PerformanceUseCase {

    private final PerformancePort performancePort;

    @Override
    public Performance createPerformance(CreatePerformanceCommand command) {
        Performance performance = new Performance(
                command.title(),
                command.location(),
                command.ageLimit(),
                command.description()
        );

        performancePort.save(performance);

        return performance;
    }
}
