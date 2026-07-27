package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.CorporationRefreshToken;

import java.util.UUID;

public interface LoadCorporationRefreshTokenPort {


    CorporationRefreshToken loadCorporationRefreshTokenByCorporationId(
            UUID corporationId
    );


    CorporationRefreshToken loadCorporationRefreshToken(
            String refreshToken
    );
}