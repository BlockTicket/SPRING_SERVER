package tikitaka.service.term.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tikitaka.service.term.application.port.in.data.UpdateTermCommand;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.application.port.out.SaveTermPort;
import tikitaka.service.term.domain.Term;
import tikitaka.service.term.domain.exception.TermNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTermServiceTest {

	@Mock
	private FindTermPort findTermPort;

	@Mock
	private SaveTermPort saveTermPort;

	@InjectMocks
	private UpdateTermService updateTermService;

	@Test
	void updateTerm() {

		Term term = Term.create("기존 제목", "기존 내용");
		UpdateTermCommand command = new UpdateTermCommand(term.getId(), "수정된 제목", "수정된 내용");
		when(findTermPort.findById(term.getId())).thenReturn(Optional.of(term));

		updateTermService.updateTerm(command);

		assertThat(term.getTitle()).isEqualTo(command.title());
		assertThat(term.getContent()).isEqualTo(command.content());
		verify(saveTermPort).save(term);
	}

	@Test
	void updateTermWhenTermDoesNotExist() {

		UUID termId = UUID.randomUUID();
		UpdateTermCommand command = new UpdateTermCommand(termId, "수정된 제목", "수정된 내용");
		when(findTermPort.findById(termId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateTermService.updateTerm(command))
				.isInstanceOf(TermNotFoundException.class);
		verify(saveTermPort, never()).save(org.mockito.ArgumentMatchers.any(Term.class));
	}
}
