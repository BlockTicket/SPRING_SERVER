package tikitaka.service.auth.application.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.member.RefreshMemberTokenCommand;
import tikitaka.service.auth.application.port.in.member.RefreshMemberTokenUseCase;
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
public class RefreshMemberTokenService implements RefreshMemberTokenUseCase {

	private final ParseTokenPort parseTokenPort;
	private final FindRefreshTokenPort findRefreshTokenPort;
	private final IssueAccessTokenPort issueAccessTokenPort;
	private final SaveAccessTokenPort saveAccessTokenPort;

	@Override
	public String refresh(
			RefreshMemberTokenCommand refreshMemberTokenCommand
	) {

		TokenPayload payload = parseTokenPort.parse(refreshMemberTokenCommand.refreshToken());

		RefreshToken stored = findRefreshTokenPort
				.findByUserIdAndRole(payload.userId(), Role.MEMBER)
				.orElseThrow(RefreshTokenNotFoundException::new);

		if (!stored.getToken().equals(refreshMemberTokenCommand.refreshToken())) {

			throw new RefreshTokenMismatchException();
		}

		IssueAccessTokenPort.IssuedAccessToken issued = issueAccessTokenPort.issueAccessToken(
				payload.userId(),
				Role.MEMBER
		);

		saveAccessTokenPort.save(
				payload.userId(),
				Role.MEMBER,
				issued.token(),
				issued.ttl()
		);

		return issued.token();
	}
}
