package tikitaka.service.term.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import tikitaka.service.term.adapter.out.persistence.TermJpaEntity;
import tikitaka.service.term.domain.Term;

@Component
public class TermMapper {

	public TermJpaEntity toEntity(Term term) {

		return TermJpaEntity.builder()
				.id(term.getId())
				.title(term.getTitle())
				.content(term.getContent())
				.build();
	}

	public Term toDomain(TermJpaEntity termJpaEntity) {

		return Term.of(
				termJpaEntity.getId(),
				termJpaEntity.getTitle(),
				termJpaEntity.getContent()
		);
	}
}
