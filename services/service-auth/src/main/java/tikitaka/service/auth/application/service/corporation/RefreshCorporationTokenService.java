package tikitaka.service.auth.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.corporation.RefreshCorporationTokenCommand;
import tikitaka.service.auth.application.port.in.corporation.RefreshCorporationTokenUseCase;
import tikitaka.service.auth.application.port.out.access_token.SaveAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.IssueAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.ParseTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.application.port.out.refresh_token.FindRefreshTokenPort;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenMismatchException;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenNotFoundException;
import tikitaka.service.auth.domain.refresh_token.RefreshToken;
import tikitaka.service.auth.domain.role.Role;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RefreshCorporationTokenService implements RefreshCorporationTokenUseCase {

	private final ParseTokenPort parseTokenPort;
	private final FindRefreshTokenPort findRefreshTokenPort;
	private final IssueAccessTokenPort issueAccessTokenPort;
	private final SaveAccessTokenPort saveAccessTokenPort;

	@Override
	public String refresh(
			RefreshCorporationTokenCommand refreshCorporationTokenCommand
	) {

		TokenPayload payload = parseTokenPort.parse(refreshCorporationTokenCommand.refreshToken());

		RefreshToken stored = findRefreshTokenPort
				.findByUserIdAndRole(payload.userId(), Role.CORPORATION)
				.orElseThrow(RefreshTokenNotFoundException::new);

		if (!stored.getToken().equals(refreshCorporationTokenCommand.refreshToken())) {

			throw new RefreshTokenMismatchException();
		}

		IssueAccessTokenPort.IssuedAccessToken issued = issueAccessTokenPort.issueAccessToken(
				payload.userId(),
				Role.CORPORATION
		);

		saveAccessTokenPort.save(
				payload.userId(),
				Role.CORPORATION,
				issued.token(),
				issued.ttl()
		);

		return issued.token();
	}
}
