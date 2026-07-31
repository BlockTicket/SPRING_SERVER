package tikitaka.service.performance.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.performance.application.port.in.data.CreatePerformanceCommand;

public record CreatePerformanceRequest(
        @NotBlank
        String title,

        @NotBlank
        String location,

        @NotBlank
        String ageLimit,

        @NotBlank
        String description,

        String imageUrl
) {
        public CreatePerformanceCommand toCommand() {
                return new CreatePerformanceCommand(
                        this.title,
                        this.location,
                        this.ageLimit,
                        this.description,
                        this.imageUrl
                );
        }
}
