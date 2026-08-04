package tikitaka.service.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.LoginMemberResult;
import tikitaka.service.auth.application.port.in.RefreshMemberCommand;
import tikitaka.service.auth.application.port.in.RefreshMemberUseCase;
import tikitaka.service.auth.application.port.out.JwtPort;
import tikitaka.service.auth.application.port.out.LoadMemberRefreshTokenPort;
import tikitaka.service.auth.domain.MemberRefreshToken;
import tikitaka.service.auth.domain.exception.exception.ExpiredRefreshTokenException;
import tikitaka.service.auth.domain.exception.exception.InvalidRefreshTokenException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRefreshService implements RefreshMemberUseCase {

    private final LoadMemberRefreshTokenPort loadMemberRefreshTokenPort;

    private final JwtPort jwtPort;

    @Override
    public LoginMemberResult refresh(
            RefreshMemberCommand refreshMemberCommand
    ) {

        String refreshToken =
                refreshMemberCommand.refreshToken();

        if (!jwtPort.validateToken(refreshToken)) {

            throw new InvalidRefreshTokenException();
        }

        MemberRefreshToken memberRefreshToken =
                loadMemberRefreshTokenPort.loadMemberRefreshToken(
                        refreshToken
                );

        if (memberRefreshToken.getExpiredAt()
                .isBefore(LocalDateTime.now())) {

            throw new ExpiredRefreshTokenException();
        }

        String accessToken =
                jwtPort.createAccessToken(
                        memberRefreshToken.getMemberId(),
                        "MEMBER"
                );

        return new LoginMemberResult(
                accessToken,
                refreshToken
        );
    }
}
