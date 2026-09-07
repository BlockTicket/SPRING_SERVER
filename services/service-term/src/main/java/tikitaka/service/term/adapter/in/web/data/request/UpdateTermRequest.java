package tikitaka.service.term.adapter.in.web.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tikitaka.service.term.application.port.in.data.UpdateTermCommand;

import java.util.UUID;

public record UpdateTermRequest(
		@NotNull
		UUID id,

		@NotBlank
		@JsonProperty("term_title")
		String termTitle,

		@NotBlank
		@JsonProperty("term_content")
		String termContent
) {

	public UpdateTermCommand toCommand() {

		return new UpdateTermCommand(id, termTitle, termContent);
	}
}
