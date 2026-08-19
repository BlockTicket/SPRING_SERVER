package tikitaka.service.auth.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.auth.SignOutCommand;
import tikitaka.service.auth.application.port.in.auth.SignOutUseCase;
import tikitaka.service.auth.application.port.out.access_token.DeleteAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.ParseTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.application.port.out.refresh_token.DeleteRefreshTokenPort;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SignOutService implements SignOutUseCase {

	private final ParseTokenPort parseTokenPort;
	private final DeleteRefreshTokenPort deleteRefreshTokenPort;
	private final DeleteAccessTokenPort deleteAccessTokenPort;

	@Override
	public void signout(
			SignOutCommand signoutCommand
	) {

		TokenPayload payload = parseTokenPort.parse(signoutCommand.accessToken());

		deleteRefreshTokenPort.deleteByUserIdAndRole(payload.userId(), signoutCommand.role());
		deleteAccessTokenPort.deleteByUserIdAndRole(payload.userId(), signoutCommand.role());
	}
}
