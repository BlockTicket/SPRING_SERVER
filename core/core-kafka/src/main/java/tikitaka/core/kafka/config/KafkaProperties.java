package tikitaka.core.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "tikitaka.kafka")
public record KafkaProperties(

        @DefaultValue("localhost:29092")
        String bootstrapServers,

        @DefaultValue
        Consumer consumer
) {

    public record Consumer(

            @DefaultValue("group-1")
            String groupId,

            @DefaultValue("earliest")
            String autoOffsetReset,

            @DefaultValue("tikitaka.*")
            String trustedPackages
    ) {
    }
}