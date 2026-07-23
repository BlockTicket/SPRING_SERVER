package tikitaka.service.performance.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.performance.application.port.in.CreatePerformanceUseCase;
import tikitaka.service.performance.application.port.in.data.CreatePerformanceCommand;
import tikitaka.service.performance.application.port.out.PerformancePort;
import tikitaka.service.performance.domain.Performance;

@Service
@RequiredArgsConstructor
public class PerformanceService implements CreatePerformanceUseCase {

    private final PerformancePort performancePort;

    @Override
    public Performance createPerformance(CreatePerformanceCommand command) {
        return performancePort.save(Performance.create(
                command.title(), command.description(), command.location(), command.ageLimit()
        ));
    }
}