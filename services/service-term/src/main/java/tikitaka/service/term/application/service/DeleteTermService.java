package tikitaka.service.term.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.term.application.port.in.DeleteTermUseCase;
import tikitaka.service.term.application.port.out.DeleteTermPort;
import tikitaka.service.term.application.port.out.FindTermPort;
import tikitaka.service.term.domain.exception.TermNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeleteTermService implements DeleteTermUseCase {

	private final FindTermPort findTermPort;
	private final DeleteTermPort deleteTermPort;

	@Override
	public void deleteTerm(UUID id) {

		findTermPort.findById(id).orElseThrow(TermNotFoundException::new);
		deleteTermPort.deleteById(id);
	}
}
