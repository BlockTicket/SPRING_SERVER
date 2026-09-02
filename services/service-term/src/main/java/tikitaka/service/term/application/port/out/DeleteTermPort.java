package tikitaka.service.term.application.port.out;

import java.util.UUID;

public interface DeleteTermPort {

	void deleteById(UUID id);
}
