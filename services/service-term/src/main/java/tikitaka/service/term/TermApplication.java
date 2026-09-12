package tikitaka.service.term;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import tikitaka.core.common.handler.GlobalExceptionHandler;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
public class TermApplication {

	public static void main(String[] args) {

		SpringApplication.run(TermApplication.class, args);
	}
}
