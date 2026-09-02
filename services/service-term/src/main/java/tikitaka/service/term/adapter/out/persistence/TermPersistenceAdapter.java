package tikitaka.service.term.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.term.adapter.out.persistence.mapper.TermMapper;
import tikitaka.service.term.application.port.out.DeleteTermPort;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.application.port.out.SaveTermPort;
import tikitaka.service.term.domain.Term;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TermPersistenceAdapter implements SaveTermPort, FindTermPort, DeleteTermPort {

	private final TermJpaRepository termJpaRepository;
	private final TermMapper termMapper;

	@Override
	public void save(Term term) {

		termJpaRepository.save(termMapper.toEntity(term));
	}

	@Override
	public List<Term> findAll() {

		return termJpaRepository.findAll().stream()
				.map(termMapper::toDomain)
				.toList();
	}

	@Override
	public Optional<Term> findById(UUID id) {

		return termJpaRepository.findById(id).map(termMapper::toDomain);
	}

	@Override
	public void deleteById(UUID id) {

		termJpaRepository.deleteById(id);
	}
}
