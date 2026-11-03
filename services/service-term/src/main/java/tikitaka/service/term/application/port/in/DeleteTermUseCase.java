package tikitaka.service.term.application.port.in;

import java.util.UUID;

public interface DeleteTermUseCase {

	void deleteTerm(UUID id);
}
