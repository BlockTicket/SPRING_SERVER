package tikitaka.core.kafka.event;

public record PerformanceUploadEvent(
    String fileType,
    String s3Key
) { }
