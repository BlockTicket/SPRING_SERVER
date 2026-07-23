package tikitaka.service.performance.application.port.in.data;

public record CreatePerformanceCommand(
        String title,
        String description,
        String location,
        String ageLimit
) {
}