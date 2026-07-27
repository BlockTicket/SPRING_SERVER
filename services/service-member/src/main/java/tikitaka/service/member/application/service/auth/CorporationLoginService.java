package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;
import tikitaka.service.member.application.port.in.auth.CorporationLoginCommand;
import tikitaka.service.member.application.port.in.auth.CorporationLoginUseCase;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.application.port.out.corporation.LoadCorporationPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.auth.AuthException;

@Service
@RequiredArgsConstructor
public class CorporationLoginService implements CorporationLoginUseCase {

	private final LoadCorporationPort loadCorporationPort;
	private final PasswordEncoder passwordEncoder;
	private final JwtPort jwtPort;
	private final RefreshTokenService refreshTokenService;

	@Override
	public LoginResponse corporationLogin(
			CorporationLoginCommand corporationLoginCommand
	) {

		Corporation corporation = loadCorporationPort.loadCorporationByUsername(
				corporationLoginCommand.username()
		);

		if (!passwordEncoder.matches(
				corporationLoginCommand.password(),
				corporation.getPassword()
		)) throw new AuthException();

		String accessToken = jwtPort.generateAccessToken(corporation.getId());
		String refreshToken = jwtPort.generateRefreshToken(corporation.getId());

		refreshTokenService.saveCorporationRefreshToken(
				corporation.getId(),
				refreshToken
		);

		return new LoginResponse(accessToken, refreshToken);
	}
}
