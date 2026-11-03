package tikitaka.service.term.application.port.in.data;

public record CreateTermCommand(
		String title,
		String content
) {
}
