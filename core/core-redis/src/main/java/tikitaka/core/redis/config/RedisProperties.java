package tikitaka.core.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "tikitaka.redis")
public record RedisProperties(
        @DefaultValue("localhost")
        String host,

        @DefaultValue("6379")
        int port,

        @DefaultValue("")
        String password
) {
}
