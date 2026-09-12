package tikitaka.service.term.application.port.out;

import tikitaka.service.term.domain.Term;

public interface SaveTermPort {

	void save(Term term);
}
