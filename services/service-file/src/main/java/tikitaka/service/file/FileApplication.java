package tikitaka.service.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
		"tikitaka.service.file",
		"tikitaka.core.common.handler",
		"tikitaka.core.kafka"
})
@ConfigurationPropertiesScan
public class FileApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileApplication.class, args);
	}
}