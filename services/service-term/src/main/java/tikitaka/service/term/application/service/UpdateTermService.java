package tikitaka.service.term.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.term.application.port.in.UpdateTermUseCase;
import tikitaka.service.term.application.port.in.data.UpdateTermCommand;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.application.port.out.SaveTermPort;
import tikitaka.service.term.domain.Term;
import tikitaka.service.term.domain.exception.TermNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateTermService implements UpdateTermUseCase {

	private final FindTermPort findTermPort;
	private final SaveTermPort saveTermPort;

	@Override
	public void updateTerm(UpdateTermCommand updateTermCommand) {

		Term term = findTermPort.findById(updateTermCommand.id())
				.orElseThrow(TermNotFoundException::new);

		term.update(updateTermCommand.title(), updateTermCommand.content());
		saveTermPort.save(term);
	}
}
