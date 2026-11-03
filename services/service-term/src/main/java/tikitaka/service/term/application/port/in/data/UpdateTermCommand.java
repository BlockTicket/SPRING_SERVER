package tikitaka.service.term.application.port.in.data;

import java.util.UUID;

public record UpdateTermCommand(
		UUID id,
		String title,
		String content
) {
}
