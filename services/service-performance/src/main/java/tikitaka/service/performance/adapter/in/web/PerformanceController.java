package tikitaka.service.performance.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.performance.adapter.in.web.data.request.CreatePerformanceRequest;
import tikitaka.service.performance.application.port.in.PerformanceUseCase;

@RestController
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceUseCase performanceUseCase;

    @PostMapping
    public CommonResponse<Void> createPerformance(
            @RequestBody @Valid CreatePerformanceRequest request
    ) {

        performanceUseCase.createPerformance(request.toCommand());

        return CommonResponse.ok("공연이 성공적으로 등록이 되었습니다.");
    }

    @GetMapping("/health")
    public String health() {

        return "health";
    }
}