package tikitaka.service.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import tikitaka.core.common.handler.GlobalExceptionHandler;
import tikitaka.core.kafka.config.KafkaConfig;

@SpringBootApplication
@Import({ GlobalExceptionHandler.class, KafkaConfig.class })
public class ServiceMemberApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServiceMemberApplication.class, args);
	}

}
