package tikitaka.service.member.application.port.in.corporation;

import tikitaka.service.member.domain.enums.UserType;

import java.util.UUID;

public record RegisterCorporationCommand(
		UUID id,
		String username,
		String email,
		String phone,
		String password,
		UserType userType
) {
}
