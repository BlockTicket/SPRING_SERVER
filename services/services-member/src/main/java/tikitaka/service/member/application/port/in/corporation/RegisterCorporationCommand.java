package tikitaka.service.member.application.port.in.corporation;

import tikitaka.service.member.domain.enums.UserType;
import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

public record RegisterCorporationCommand(
		UUID id,
		String username,
		String email,
		String phone,
		String password,
		UserType userType,
		NtsBusiness ntsBusiness
) {
}
