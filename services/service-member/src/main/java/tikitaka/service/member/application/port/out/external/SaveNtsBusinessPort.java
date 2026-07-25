package tikitaka.service.member.application.port.out.external;

import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

public interface SaveNtsBusinessPort {

	void saveNtsBusiness(
			UUID corporationId,
			NtsBusiness ntsBusiness
	);
}
