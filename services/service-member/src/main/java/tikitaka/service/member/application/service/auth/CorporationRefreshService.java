package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.in.auth.LoginCorporationResult;
import tikitaka.service.member.application.port.in.auth.RefreshCorporationCommand;
import tikitaka.service.member.application.port.in.auth.RefreshCorporationUseCase;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.application.port.out.auth.LoadCorporationRefreshTokenPort;
import tikitaka.service.member.domain.auth.CorporationRefreshToken;
import tikitaka.service.member.domain.exception.exception.auth.ExpiredRefreshTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

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