package tikitaka.service.member.application.port.out.corporation;

import tikitaka.service.member.domain.corporation.Corporation;

public interface LoadCorporationPort {

	Corporation loadCorporationByUsername(String username);
}
