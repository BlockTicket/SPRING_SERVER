package tikitaka.service.member.adapter.in.security;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MemberAuthentication {


	private final UUID id;

	private final String type;


	public MemberAuthentication(
			UUID id,
			String type
	) {

		this.id = id;
		this.type = type;
	}
}