package tikitaka.service.term.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Term {

	private final UUID id;
	private String title;
	private String content;

	public static Term create(
			String title,
			String content
	) {

		return new Term(UUID.randomUUID(), title, content);
	}

	public static Term of(
			UUID id,
			String title,
			String content
	) {

		return new Term(id, title, content);
	}

	public void update(
			String title,
			String content
	) {

		this.title = title;
		this.content = content;
	}
}
