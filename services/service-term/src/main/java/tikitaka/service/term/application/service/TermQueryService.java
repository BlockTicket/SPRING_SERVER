package tikitaka.service.term.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.term.application.port.in.GetTermUseCase;
import tikitaka.service.term.application.port.in.GetTermsUseCase;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.domain.Term;
import tikitaka.service.term.domain.exception.TermNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermQueryService implements GetTermUseCase, GetTermsUseCase {

	private final FindTermPort findTermPort;

	@Override
	public List<Term> getTerms() {

		return findTermPort.findAll();
	}

	@Override
	public Term getTerm(UUID id) {

		return findTermPort.findById(id).orElseThrow(TermNotFoundException::new);
	}
}
