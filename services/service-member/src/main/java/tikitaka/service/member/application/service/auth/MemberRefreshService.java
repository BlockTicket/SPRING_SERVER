package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.in.auth.LoginMemberResult;
import tikitaka.service.member.application.port.in.auth.RefreshMemberCommand;
import tikitaka.service.member.application.port.in.auth.RefreshMemberUseCase;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.application.port.out.auth.LoadMemberRefreshTokenPort;
import tikitaka.service.member.domain.auth.MemberRefreshToken;
import tikitaka.service.member.domain.exception.exception.auth.ExpiredRefreshTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

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