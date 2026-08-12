package tikitaka.service.auth.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.auth.SignInCommand;
import tikitaka.service.auth.application.port.in.auth.SignInResult;
import tikitaka.service.auth.application.port.in.auth.SignInUseCase;
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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SignInService implements SignInUseCase {

	private final FindCredentialPort findCredentialPort;
	private final PasswordMatcherPort passwordMatcherPort;
	private final IssueAccessTokenPort issueAccessTokenPort;
	private final IssueRefreshTokenPort issueRefreshTokenPort;
	private final SaveRefreshTokenPort saveRefreshTokenPort;
	private final SaveAccessTokenPort saveAccessTokenPort;

	@Override
	public SignInResult signin(
			SignInCommand signinCommand
	) {

		Credential credential = findCredentialPort
				.findByUsernameAndRole(signinCommand.username(), signinCommand.role())
				.orElseThrow(CredentialNotFoundException::new);

		if (!passwordMatcherPort.matches(signinCommand.password(), credential.getPassword())) throw new InvalidPasswordException();

		IssueAccessTokenPort.IssuedAccessToken access = issueAccessTokenPort.issueAccessToken(
				credential.getId(),
				signinCommand.role()
		);

		IssueRefreshTokenPort.IssuedRefreshToken refresh = issueRefreshTokenPort.issueRefreshToken(
				credential.getId(),
				signinCommand.role()
		);

		saveRefreshTokenPort.save(new RefreshToken(
				credential.getId(),
				signinCommand.role(),
				refresh.token(),
				LocalDateTime.now().plus(refresh.ttl())
		));

		saveAccessTokenPort.save(
				credential.getId(),
				signinCommand.role(),
				access.token(),
				access.ttl()
		);

		return new SignInResult(
				access.token(),
				refresh.token(),
				refresh.ttl()
		);
	}
}
