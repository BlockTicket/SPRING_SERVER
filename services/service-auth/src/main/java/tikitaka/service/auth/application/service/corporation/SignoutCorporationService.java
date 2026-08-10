package tikitaka.service.auth.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.corporation.SignoutCorporationCommand;
import tikitaka.service.auth.application.port.in.corporation.SignoutCorporationUseCase;
import tikitaka.service.auth.application.port.out.access_token.DeleteAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.ParseTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.application.port.out.refresh_token.DeleteRefreshTokenPort;
import tikitaka.service.auth.domain.role.Role;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SignoutCorporationService implements SignoutCorporationUseCase {

	private final ParseTokenPort parseTokenPort;
	private final DeleteRefreshTokenPort deleteRefreshTokenPort;
	private final DeleteAccessTokenPort deleteAccessTokenPort;

	@Override
	public void signout(
			SignoutCorporationCommand signoutCorporationCommand
	) {

		TokenPayload payload = parseTokenPort.parse(signoutCorporationCommand.accessToken());

		deleteRefreshTokenPort.deleteByUserIdAndRole(payload.userId(), Role.CORPORATION);
		deleteAccessTokenPort.deleteByUserIdAndRole(payload.userId(), Role.CORPORATION);
	}
}
