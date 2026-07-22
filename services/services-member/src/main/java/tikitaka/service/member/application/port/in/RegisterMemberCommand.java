package tikitaka.service.member.application.port.in;

import tikitaka.service.member.domain.enums.Provider;
import tikitaka.service.member.domain.enums.UserType;

import java.util.UUID;

public record RegisterMemberCommand(
		UUID id,
		String username,
		String email,
		String phone,
		String password,
		UserType userType,
		Provider provider
) {
}
