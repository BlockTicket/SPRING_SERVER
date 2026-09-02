package tikitaka.service.term.adapter.in.web.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import tikitaka.service.term.application.port.in.data.UpdateTermCommand;

import java.util.UUID;

public record UpdateTermRequest(
		@NotBlank
		@JsonProperty("term_title")
		String termTitle,

		@NotBlank
		@JsonProperty("term_content")
		String termContent
) {

	public UpdateTermCommand toCommand(UUID id) {

		return new UpdateTermCommand(id, termTitle, termContent);
	}
}
