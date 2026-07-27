package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.corporation.Corporation;

public interface LoadCorporationPort {

	Corporation loadCorporationByUsername(
			String username
	);
}