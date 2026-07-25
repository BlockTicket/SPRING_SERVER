package tikitaka.service.file.adapter.out.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public record S3Properties(
        String region,
        String bucket
) { }