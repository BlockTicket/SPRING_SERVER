package tikitaka.service.term.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.term.application.port.in.CreateTermUseCase;
import tikitaka.service.term.application.port.in.data.CreateTermCommand;
import tikitaka.service.term.application.port.out.SaveTermPort;
import tikitaka.service.term.domain.Term;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateTermService implements CreateTermUseCase {

	private final SaveTermPort saveTermPort;

	@Override
	public UUID createTerm(CreateTermCommand createTermCommand) {

		Term term = Term.create(createTermCommand.title(), createTermCommand.content());

		saveTermPort.save(term);

		return term.getId();
	}
}
