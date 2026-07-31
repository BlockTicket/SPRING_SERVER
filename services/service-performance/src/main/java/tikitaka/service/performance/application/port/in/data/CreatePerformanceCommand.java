package tikitaka.service.performance.application.port.in.data;

public record CreatePerformanceCommand(
        String title,
        String location,
        String ageLimit,
        String description,
        String imageUrl
) {
}
