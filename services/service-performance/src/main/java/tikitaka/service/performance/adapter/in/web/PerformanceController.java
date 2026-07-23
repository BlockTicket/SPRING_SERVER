package tikitaka.service.performance.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.performance.adapter.in.web.data.CreatePerformanceRequest;
import tikitaka.service.performance.application.port.in.data.CreatePerformanceCommand;
import tikitaka.service.performance.application.service.PerformanceService;

@RestController
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @PostMapping
    public CommonResponse<Void> createPerformance(
            @RequestBody @Valid CreatePerformanceRequest request
    ) {
        performanceService.createPerformance(request.toCommand());

        return CommonResponse.ok("공연이 정식적으로 등록되었습니다.");
    }
}
