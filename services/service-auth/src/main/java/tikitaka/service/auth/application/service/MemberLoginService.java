package tikitaka.service.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.LoginMemberCommand;
import tikitaka.service.auth.application.port.in.LoginMemberResult;
import tikitaka.service.auth.application.port.in.LoginMemberUseCase;
import tikitaka.service.auth.application.port.out.JwtPort;
import tikitaka.service.auth.application.port.out.LoadMemberCredentialPort;
import tikitaka.service.auth.application.port.out.LoadMemberRefreshTokenPort;
import tikitaka.service.auth.application.port.out.SaveMemberRefreshTokenPort;
import tikitaka.service.auth.domain.MemberCredential;
import tikitaka.service.auth.domain.MemberRefreshToken;
import tikitaka.service.auth.domain.exception.exception.InvalidPasswordException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberLoginService implements LoginMemberUseCase {

    private final LoadMemberCredentialPort loadMemberCredentialPort;

    private final LoadMemberRefreshTokenPort loadMemberRefreshTokenPort;

    private final SaveMemberRefreshTokenPort saveMemberRefreshTokenPort;

    private final PasswordEncoder passwordEncoder;

    private final JwtPort jwtPort;

    @Override
    public LoginMemberResult loginMember(
            LoginMemberCommand loginMemberCommand
    ) {

        MemberCredential member =
                loadMemberCredentialPort.loadMemberByUsername(
                        loginMemberCommand.username()
                );

        if (!passwordEncoder.matches(
                loginMemberCommand.password(),
                member.getPassword()
        )) {

            throw new InvalidPasswordException();
        }

        String accessToken =
                jwtPort.createAccessToken(
                        member.getId(),
                        "MEMBER"
                );

        String refreshToken = null;

        if (loginMemberCommand.rememberMe()) {

            refreshToken =
                    createOrLoadRefreshToken(
                            member
                    );
        }

        return new LoginMemberResult(
                accessToken,
                refreshToken
        );
    }

    private String createOrLoadRefreshToken(
            MemberCredential member
    ) {

        MemberRefreshToken savedToken =
                loadMemberRefreshTokenPort
                        .loadMemberRefreshTokenByMemberId(
                                member.getId()
                        );

        if (savedToken != null &&
                savedToken.getExpiredAt().isAfter(LocalDateTime.now())) {

            return savedToken.getRefreshToken();
        }

        String refreshToken =
                jwtPort.createRefreshToken(
                        member.getId(),
                        "MEMBER"
                );

        saveMemberRefreshTokenPort.saveMemberRefreshToken(
                new MemberRefreshToken(
                        member.getId(),
                        refreshToken,
                        LocalDateTime.now().plusDays(30)
                )
        );

        return refreshToken;
    }
}
