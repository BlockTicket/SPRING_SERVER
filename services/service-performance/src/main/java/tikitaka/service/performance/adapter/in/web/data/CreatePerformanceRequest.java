package tikitaka.service.performance.adapter.in.web.data;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.performance.application.port.in.data.CreatePerformanceCommand;

public record CreatePerformanceRequest(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotBlank
        String location,
        @NotBlank
        String ageLimit
) {
    public CreatePerformanceCommand toCommand() {
        return new CreatePerformanceCommand(
                this.title, this.description, this.location, this.ageLimit
        );
    }
}
