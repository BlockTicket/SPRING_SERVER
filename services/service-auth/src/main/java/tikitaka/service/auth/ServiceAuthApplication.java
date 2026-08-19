package tikitaka.service.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import tikitaka.core.common.handler.GlobalExceptionHandler;
import tikitaka.core.kafka.config.KafkaConfig;
import tikitaka.core.redis.config.RedisConfig;

@SpringBootApplication
@ConfigurationPropertiesScan
@Import({ GlobalExceptionHandler.class, KafkaConfig.class, RedisConfig.class })
public class ServiceAuthApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServiceAuthApplication.class, args);
	}
}
