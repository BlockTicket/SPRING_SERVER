package tikitaka.service.term.application.port.in;

import tikitaka.service.term.domain.Term;

import java.util.List;

public interface GetTermsUseCase {

	List<Term> getTerms();
}
