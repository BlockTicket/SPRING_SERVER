package tikitaka.service.term.application.port.out;

import tikitaka.service.term.domain.Term;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindTermPort {

	List<Term> findAll();

	Optional<Term> findById(UUID id);
}
