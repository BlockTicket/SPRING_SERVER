package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.in.auth.LoginCorporationCommand;
import tikitaka.service.member.application.port.in.auth.LoginCorporationResult;
import tikitaka.service.member.application.port.in.auth.LoginCorporationUseCase;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.application.port.out.auth.LoadCorporationPort;
import tikitaka.service.member.application.port.out.auth.LoadCorporationRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.SaveCorporationRefreshTokenPort;
import tikitaka.service.member.domain.auth.CorporationRefreshToken;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.auth.InvalidPasswordException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationLoginService implements LoginCorporationUseCase {


    private final LoadCorporationPort loadCorporationPort;

    private final LoadCorporationRefreshTokenPort loadCorporationRefreshTokenPort;

    private final SaveCorporationRefreshTokenPort saveCorporationRefreshTokenPort;

    private final PasswordEncoder passwordEncoder;

    private final JwtPort jwtPort;


    @Override
    public LoginCorporationResult loginCorporation(
            LoginCorporationCommand loginCorporationCommand
    ) {


        Corporation corporation =
                loadCorporationPort.loadCorporationByUsername(
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
            Corporation corporation
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