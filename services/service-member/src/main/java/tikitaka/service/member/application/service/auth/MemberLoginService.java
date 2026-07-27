package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;
import tikitaka.service.member.application.port.in.auth.MemberLoginCommand;
import tikitaka.service.member.application.port.in.auth.MemberLoginUseCase;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.application.port.out.member.LoadMemberPort;
import tikitaka.service.member.domain.exception.exception.auth.AuthException;
import tikitaka.service.member.domain.member.Member;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements MemberLoginUseCase {

	private final LoadMemberPort loadMemberPort;
	private final PasswordEncoder passwordEncoder;
	private final JwtPort jwtPort;
	private final RefreshTokenService refreshTokenService;

	@Override
	public LoginResponse memberLogin(
			MemberLoginCommand memberLoginCommand
	) {

		Member member = loadMemberPort.loadMemberByUsername(
				memberLoginCommand.username()
		);

		if (!passwordEncoder.matches(
				memberLoginCommand.password(),
				member.getPassword()
		)) throw new AuthException();

		String accessToken = jwtPort.generateAccessToken(member.getId());
		String refreshToken = jwtPort.generateRefreshToken(member.getId());

		refreshTokenService.saveMemberRefreshToken(
				member.getId(),
				refreshToken
		);

		return new LoginResponse(accessToken, refreshToken);
	}
}
