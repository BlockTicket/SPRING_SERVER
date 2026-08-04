package tikitaka.service.auth.application.port.out;

import tikitaka.service.auth.domain.CorporationRefreshToken;

public interface SaveCorporationRefreshTokenPort {

    void saveCorporationRefreshToken(
            CorporationRefreshToken corporationRefreshToken
    );
}
