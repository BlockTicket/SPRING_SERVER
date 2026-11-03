package tikitaka.service.term.application.port.in;

import tikitaka.service.term.domain.Term;

import java.util.UUID;

public interface GetTermUseCase {

	Term getTerm(UUID id);
}
