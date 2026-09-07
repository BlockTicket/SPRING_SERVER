package tikitaka.service.term.adapter.in.web.data.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TermRequestValidationTest {

	private ValidatorFactory validatorFactory;
	private Validator validator;
	private final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");

	@BeforeEach
	void setUp() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterEach
	void tearDown() {
		validatorFactory.close();
	}

	@Test
	void acceptCreateTitleAtMaximumLength() {
		CreateTermRequest request = new CreateTermRequest("가".repeat(255), "약관 내용");

		assertThat(validator.validate(request)).isEmpty();
	}

	@Test
	void rejectCreateTitleOverMaximumLength() {
		CreateTermRequest request = new CreateTermRequest("가".repeat(256), "약관 내용");

		assertThat(validator.validate(request))
				.extracting(violation -> violation.getPropertyPath().toString())
				.containsExactly("termTitle");
	}

	@Test
	void acceptUpdateTitleAtMaximumLength() {
		UpdateTermRequest request = new UpdateTermRequest(id, "가".repeat(255), "약관 내용");

		assertThat(validator.validate(request)).isEmpty();
	}

	@Test
	void rejectUpdateTitleOverMaximumLength() {
		UpdateTermRequest request = new UpdateTermRequest(id, "가".repeat(256), "약관 내용");

		assertThat(validator.validate(request))
				.extracting(violation -> violation.getPropertyPath().toString())
				.containsExactly("termTitle");
	}
}
