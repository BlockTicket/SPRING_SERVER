package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.CorporationRefreshToken;

public interface SaveCorporationRefreshTokenPort {

	void saveCorporationRefreshToken(
			CorporationRefreshToken corporationRefreshToken
	);
}