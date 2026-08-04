package tikitaka.service.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.LoginCorporationResult;
import tikitaka.service.auth.application.port.in.RefreshCorporationCommand;
import tikitaka.service.auth.application.port.in.RefreshCorporationUseCase;
import tikitaka.service.auth.application.port.out.JwtPort;
import tikitaka.service.auth.application.port.out.LoadCorporationRefreshTokenPort;
import tikitaka.service.auth.domain.CorporationRefreshToken;
import tikitaka.service.auth.domain.exception.exception.ExpiredRefreshTokenException;
import tikitaka.service.auth.domain.exception.exception.InvalidRefreshTokenException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporationRefreshService implements RefreshCorporationUseCase {

    private final LoadCorporationRefreshTokenPort loadCorporationRefreshTokenPort;

    private final JwtPort jwtPort;

    @Override
    public LoginCorporationResult refresh(
            RefreshCorporationCommand refreshCorporationCommand
    ) {

        String refreshToken =
                refreshCorporationCommand.refreshToken();

        if (!jwtPort.validateToken(refreshToken)) {

            throw new InvalidRefreshTokenException();
        }

        CorporationRefreshToken corporationRefreshToken =
                loadCorporationRefreshTokenPort.loadCorporationRefreshToken(
                        refreshToken
                );

        if (corporationRefreshToken.getExpiredAt()
                .isBefore(LocalDateTime.now())) {

            throw new ExpiredRefreshTokenException();
        }

        String accessToken =
                jwtPort.createAccessToken(
                        corporationRefreshToken.getCorporationId(),
                        "CORPORATION"
                );

        return new LoginCorporationResult(
                accessToken,
                refreshToken
        );
    }
}
