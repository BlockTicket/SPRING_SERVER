package tikitaka.service.term.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.domain.Term;
import tikitaka.service.term.domain.exception.TermNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermQueryServiceTest {

	@Mock
	private FindTermPort findTermPort;

	@InjectMocks
	private TermQueryService termQueryService;

	@Test
	void getTerms() {

		Term firstTerm = Term.create("서비스 이용약관", "첫 번째 약관 내용");
		Term secondTerm = Term.create("개인정보 처리방침", "두 번째 약관 내용");
		when(findTermPort.findAll()).thenReturn(List.of(firstTerm, secondTerm));

		List<Term> terms = termQueryService.getTerms();

		assertThat(terms).containsExactly(firstTerm, secondTerm);
	}

	@Test
	void getTerm() {

		Term term = Term.create("서비스 이용약관", "약관 내용");
		when(findTermPort.findById(term.getId())).thenReturn(Optional.of(term));

		Term foundTerm = termQueryService.getTerm(term.getId());

		assertThat(foundTerm).isEqualTo(term);
	}

	@Test
	void getTermWhenTermDoesNotExist() {

		UUID termId = UUID.randomUUID();
		when(findTermPort.findById(termId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> termQueryService.getTerm(termId))
				.isInstanceOf(TermNotFoundException.class);
	}
}
