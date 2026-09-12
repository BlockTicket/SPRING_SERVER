package tikitaka.service.term.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tikitaka.service.term.application.port.out.DeleteTermPort;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.domain.Term;
import tikitaka.service.term.domain.exception.TermNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteTermServiceTest {

	@Mock
	private FindTermPort findTermPort;

	@Mock
	private DeleteTermPort deleteTermPort;

	@InjectMocks
	private DeleteTermService deleteTermService;

	@Test
	void deleteTerm() {

		Term term = Term.create("서비스 이용약관", "약관 내용");
		when(findTermPort.findById(term.getId())).thenReturn(Optional.of(term));

		deleteTermService.deleteTerm(term.getId());

		verify(deleteTermPort).deleteById(term.getId());
	}

	@Test
	void deleteTermWhenTermDoesNotExist() {

		UUID termId = UUID.randomUUID();
		when(findTermPort.findById(termId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> deleteTermService.deleteTerm(termId))
				.isInstanceOf(TermNotFoundException.class);
		verify(deleteTermPort, never()).deleteById(termId);
	}
}
