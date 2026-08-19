package tikitaka.service.auth.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.auth.RefreshTokenCommand;
import tikitaka.service.auth.application.port.in.auth.RefreshTokenUseCase;
import tikitaka.service.auth.application.port.out.access_token.SaveAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.IssueAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.ParseTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.application.port.out.refresh_token.FindRefreshTokenPort;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenMismatchException;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenNotFoundException;
import tikitaka.service.auth.domain.refresh_token.RefreshToken;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RefreshTokenService implements RefreshTokenUseCase {

	private final ParseTokenPort parseTokenPort;
	private final FindRefreshTokenPort findRefreshTokenPort;
	private final IssueAccessTokenPort issueAccessTokenPort;
	private final SaveAccessTokenPort saveAccessTokenPort;

	@Override
	public String refresh(
			RefreshTokenCommand refreshTokenCommand
	) {

		TokenPayload payload = parseTokenPort.parse(refreshTokenCommand.refreshToken());

		RefreshToken stored = findRefreshTokenPort
				.findByUserIdAndRole(payload.userId(), refreshTokenCommand.role())
				.orElseThrow(RefreshTokenNotFoundException::new);

		if (!stored.getToken().equals(refreshTokenCommand.refreshToken())) throw new RefreshTokenMismatchException();

		IssueAccessTokenPort.IssuedAccessToken issued = issueAccessTokenPort.issueAccessToken(
				payload.userId(),
				refreshTokenCommand.role()
		);

		saveAccessTokenPort.save(
				payload.userId(),
				refreshTokenCommand.role(),
				issued.token(),
				issued.ttl()
		);

		return issued.token();
	}
}
