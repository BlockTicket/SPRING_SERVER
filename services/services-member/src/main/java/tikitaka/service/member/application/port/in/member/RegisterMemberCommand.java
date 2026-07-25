package tikitaka.service.member.application.port.in.member;

import tikitaka.service.member.domain.member.Provider;

import java.util.UUID;

public record RegisterMemberCommand(
		UUID id,
		String username,
		String email,
		String phone,
		String password,
		Provider provider
) {
}
