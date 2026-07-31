package tikitaka.service.performance.application.port.in;

import tikitaka.service.performance.application.port.in.data.CreatePerformanceCommand;
import tikitaka.service.performance.domain.Performance;

public interface PerformanceUseCase {

    Performance createPerformance(CreatePerformanceCommand command);
}
