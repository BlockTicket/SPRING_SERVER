package tikitaka.service.auth.application.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.member.SigninMemberCommand;
import tikitaka.service.auth.application.port.in.member.SigninMemberResult;
import tikitaka.service.auth.application.port.in.member.SigninMemberUseCase;
import tikitaka.service.auth.application.port.out.access_token.SaveAccessTokenPort;
import tikitaka.service.auth.application.port.out.credential.FindCredentialPort;
import tikitaka.service.auth.application.port.out.jwt.IssueAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.IssueRefreshTokenPort;
import tikitaka.service.auth.application.port.out.password.PasswordMatcherPort;
import tikitaka.service.auth.application.port.out.refresh_token.SaveRefreshTokenPort;
import tikitaka.service.auth.domain.credential.Credential;
import tikitaka.service.auth.domain.exception.exception.CredentialNotFoundException;
import tikitaka.service.auth.domain.exception.exception.InvalidPasswordException;
import tikitaka.service.auth.domain.refresh_token.RefreshToken;
import tikitaka.service.auth.domain.role.Role;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SigninMemberService implements SigninMemberUseCase {

	private final FindCredentialPort findCredentialPort;
	private final PasswordMatcherPort passwordMatcherPort;
	private final IssueAccessTokenPort issueAccessTokenPort;
	private final IssueRefreshTokenPort issueRefreshTokenPort;
	private final SaveRefreshTokenPort saveRefreshTokenPort;
	private final SaveAccessTokenPort saveAccessTokenPort;

	@Override
	public SigninMemberResult signin(
			SigninMemberCommand signinMemberCommand
	) {

		Credential credential = findCredentialPort
				.findByUsernameAndRole(signinMemberCommand.username(), Role.MEMBER)
				.orElseThrow(CredentialNotFoundException::new);

		if (!passwordMatcherPort.matches(signinMemberCommand.password(), credential.getPassword())) {

			throw new InvalidPasswordException();
		}

		IssueAccessTokenPort.IssuedAccessToken access = issueAccessTokenPort.issueAccessToken(
				credential.getId(),
				Role.MEMBER
		);

		IssueRefreshTokenPort.IssuedRefreshToken refresh = issueRefreshTokenPort.issueRefreshToken(
				credential.getId(),
				Role.MEMBER
		);

		saveRefreshTokenPort.save(new RefreshToken(
				credential.getId(),
				Role.MEMBER,
				refresh.token(),
				LocalDateTime.now().plus(refresh.ttl())
		));

		saveAccessTokenPort.save(
				credential.getId(),
				Role.MEMBER,
				access.token(),
				access.ttl()
		);

		return new SigninMemberResult(
				access.token(),
				refresh.token(),
				refresh.ttl()
		);
	}
}
