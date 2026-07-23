package tikitaka.service.performance.application.port.out;

import tikitaka.service.performance.domain.Performance;

public interface PerformancePort {

    Performance save(Performance performance);
}