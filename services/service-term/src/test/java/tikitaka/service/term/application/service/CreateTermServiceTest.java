package tikitaka.service.term.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tikitaka.service.term.application.port.in.data.CreateTermCommand;
import tikitaka.service.term.application.port.out.SaveTermPort;
import tikitaka.service.term.domain.Term;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateTermServiceTest {

	@Mock
	private SaveTermPort saveTermPort;

	@InjectMocks
	private CreateTermService createTermService;

	@Test
	void createTerm() {

		CreateTermCommand command = new CreateTermCommand("서비스 이용약관", "약관 내용");
		ArgumentCaptor<Term> termCaptor = ArgumentCaptor.forClass(Term.class);

		UUID termId = createTermService.createTerm(command);

		verify(saveTermPort).save(termCaptor.capture());

		Term savedTerm = termCaptor.getValue();
		assertThat(termId).isEqualTo(savedTerm.getId());
		assertThat(savedTerm.getTitle()).isEqualTo(command.title());
		assertThat(savedTerm.getContent()).isEqualTo(command.content());
	}
}
