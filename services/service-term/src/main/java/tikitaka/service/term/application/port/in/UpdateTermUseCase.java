package tikitaka.service.term.application.port.in;

import tikitaka.service.term.application.port.in.data.UpdateTermCommand;

public interface UpdateTermUseCase {

	void updateTerm(UpdateTermCommand updateTermCommand);
}
