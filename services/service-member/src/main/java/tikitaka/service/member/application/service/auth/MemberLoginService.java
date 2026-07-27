package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.in.auth.LoginMemberCommand;
import tikitaka.service.member.application.port.in.auth.LoginMemberResult;
import tikitaka.service.member.application.port.in.auth.LoginMemberUseCase;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.application.port.out.auth.LoadMemberPort;
import tikitaka.service.member.application.port.out.auth.LoadMemberRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.SaveMemberRefreshTokenPort;
import tikitaka.service.member.domain.auth.MemberRefreshToken;
import tikitaka.service.member.domain.exception.exception.auth.InvalidPasswordException;
import tikitaka.service.member.domain.member.Member;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberLoginService implements LoginMemberUseCase {


	private final LoadMemberPort loadMemberPort;

	private final LoadMemberRefreshTokenPort loadMemberRefreshTokenPort;

	private final SaveMemberRefreshTokenPort saveMemberRefreshTokenPort;

	private final PasswordEncoder passwordEncoder;

	private final JwtPort jwtPort;


	@Override
	public LoginMemberResult loginMember(
			LoginMemberCommand loginMemberCommand
	) {


		Member member =
				loadMemberPort.loadMemberByUsername(
						loginMemberCommand.username()
				);


		if(!passwordEncoder.matches(
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


		if(loginMemberCommand.rememberMe()) {

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
			Member member
	) {


		try {

			MemberRefreshToken savedToken =
					loadMemberRefreshTokenPort
							.loadMemberRefreshTokenByMemberId(
									member.getId()
							);


			if(savedToken.getExpiredAt()
					.isAfter(LocalDateTime.now())) {

				return savedToken.getRefreshToken();
			}


		} catch (Exception ignored) {

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