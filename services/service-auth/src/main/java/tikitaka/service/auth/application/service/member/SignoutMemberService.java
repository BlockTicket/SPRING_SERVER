package tikitaka.service.auth.application.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.member.SignoutMemberCommand;
import tikitaka.service.auth.application.port.in.member.SignoutMemberUseCase;
import tikitaka.service.auth.application.port.out.access_token.DeleteAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.ParseTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.application.port.out.refresh_token.DeleteRefreshTokenPort;
import tikitaka.service.auth.domain.role.Role;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SignoutMemberService implements SignoutMemberUseCase {

	private final ParseTokenPort parseTokenPort;
	private final DeleteRefreshTokenPort deleteRefreshTokenPort;
	private final DeleteAccessTokenPort deleteAccessTokenPort;

	@Override
	public void signout(
			SignoutMemberCommand signoutMemberCommand
	) {

		TokenPayload payload = parseTokenPort.parse(signoutMemberCommand.accessToken());

		deleteRefreshTokenPort.deleteByUserIdAndRole(payload.userId(), Role.MEMBER);
		deleteAccessTokenPort.deleteByUserIdAndRole(payload.userId(), Role.MEMBER);
	}
}
