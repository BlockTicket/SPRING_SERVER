package tikitaka.service.term.application.port.in;

import tikitaka.service.term.application.port.in.data.CreateTermCommand;

import java.util.UUID;

public interface CreateTermUseCase {

	UUID createTerm(CreateTermCommand createTermCommand);
}
