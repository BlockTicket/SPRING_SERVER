package tikitaka.service.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.LoginCorporationCommand;
import tikitaka.service.auth.application.port.in.LoginCorporationResult;
import tikitaka.service.auth.application.port.in.LoginCorporationUseCase;
import tikitaka.service.auth.application.port.out.JwtPort;
import tikitaka.service.auth.application.port.out.LoadCorporationCredentialPort;
import tikitaka.service.auth.application.port.out.LoadCorporationRefreshTokenPort;
import tikitaka.service.auth.application.port.out.SaveCorporationRefreshTokenPort;
import tikitaka.service.auth.domain.CorporationCredential;
import tikitaka.service.auth.domain.CorporationRefreshToken;
import tikitaka.service.auth.domain.exception.exception.InvalidPasswordException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationLoginService implements LoginCorporationUseCase {

    private final LoadCorporationCredentialPort loadCorporationCredentialPort;

    private final LoadCorporationRefreshTokenPort loadCorporationRefreshTokenPort;

    private final SaveCorporationRefreshTokenPort saveCorporationRefreshTokenPort;

    private final PasswordEncoder passwordEncoder;

    private final JwtPort jwtPort;

    @Override
    public LoginCorporationResult loginCorporation(
            LoginCorporationCommand loginCorporationCommand
    ) {

        CorporationCredential corporation =
                loadCorporationCredentialPort.loadCorporationByUsername(
                        loginCorporationCommand.username()
                );

        if (!passwordEncoder.matches(
                loginCorporationCommand.password(),
                corporation.getPassword()
        )) {

            throw new InvalidPasswordException();
        }

        String accessToken =
                jwtPort.createAccessToken(
                        corporation.getId(),
                        "CORPORATION"
                );

        String refreshToken = null;

        if (loginCorporationCommand.rememberMe()) {

            refreshToken =
                    createOrLoadRefreshToken(
                            corporation
                    );
        }

        return new LoginCorporationResult(
                accessToken,
                refreshToken
        );
    }

    private String createOrLoadRefreshToken(
            CorporationCredential corporation
    ) {

        try {

            CorporationRefreshToken savedToken =
                    loadCorporationRefreshTokenPort
                            .loadCorporationRefreshTokenByCorporationId(
                                    corporation.getId()
                            );

            if (savedToken.getExpiredAt()
                    .isAfter(LocalDateTime.now())) {

                return savedToken.getRefreshToken();
            }

        } catch (Exception ignored) {

        }

        String refreshToken =
                jwtPort.createRefreshToken(
                        corporation.getId(),
                        "CORPORATION"
                );

        saveCorporationRefreshTokenPort.saveCorporationRefreshToken(
                new CorporationRefreshToken(
                        corporation.getId(),
                        refreshToken,
                        LocalDateTime.now().plusDays(30)
                )
        );

        return refreshToken;
    }
}
