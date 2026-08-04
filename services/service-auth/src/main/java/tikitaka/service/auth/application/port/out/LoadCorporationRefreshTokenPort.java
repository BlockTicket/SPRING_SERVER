package tikitaka.service.auth.application.port.out;

import tikitaka.service.auth.domain.CorporationRefreshToken;

import java.util.UUID;

public interface LoadCorporationRefreshTokenPort {

    CorporationRefreshToken loadCorporationRefreshTokenByCorporationId(
            UUID corporationId
    );

    CorporationRefreshToken loadCorporationRefreshToken(
            String refreshToken
    );
}
