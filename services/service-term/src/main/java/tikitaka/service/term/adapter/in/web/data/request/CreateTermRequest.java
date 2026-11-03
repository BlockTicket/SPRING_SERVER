package tikitaka.service.term.adapter.in.web.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tikitaka.service.term.application.port.in.data.CreateTermCommand;

public record CreateTermRequest(
		@NotBlank
		@Size(max = 255)
		@JsonProperty("term_title")
		String termTitle,

		@NotBlank
		@JsonProperty("term_content")
		String termContent
) {

	public CreateTermCommand toCommand() {

		return new CreateTermCommand(termTitle, termContent);
	}
}
