package tikitaka.service.term.adapter.in.web.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import tikitaka.service.term.domain.Term;

import java.util.UUID;

public record TermResponse(
		UUID id,

		@JsonProperty("term_title")
		String termTitle,

		@JsonProperty("term_content")
		String termContent
) {

	public static TermResponse from(Term term) {

		return new TermResponse(term.getId(), term.getTitle(), term.getContent());
	}
}
