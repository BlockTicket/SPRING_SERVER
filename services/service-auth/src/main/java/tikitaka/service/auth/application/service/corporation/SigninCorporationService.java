package tikitaka.service.auth.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.corporation.SigninCorporationCommand;
import tikitaka.service.auth.application.port.in.corporation.SigninCorporationResult;
import tikitaka.service.auth.application.port.in.corporation.SigninCorporationUseCase;
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
public class SigninCorporationService implements SigninCorporationUseCase {

	private final FindCredentialPort findCredentialPort;
	private final PasswordMatcherPort passwordMatcherPort;
	private final IssueAccessTokenPort issueAccessTokenPort;
	private final IssueRefreshTokenPort issueRefreshTokenPort;
	private final SaveRefreshTokenPort saveRefreshTokenPort;
	private final SaveAccessTokenPort saveAccessTokenPort;

	@Override
	public SigninCorporationResult signin(
			SigninCorporationCommand signinCorporationCommand
	) {

		Credential credential = findCredentialPort
				.findByUsernameAndRole(signinCorporationCommand.username(), Role.CORPORATION)
				.orElseThrow(CredentialNotFoundException::new);

		if (!passwordMatcherPort.matches(signinCorporationCommand.password(), credential.getPassword())) {

			throw new InvalidPasswordException();
		}

		IssueAccessTokenPort.IssuedAccessToken access = issueAccessTokenPort.issueAccessToken(
				credential.getId(),
				Role.CORPORATION
		);

		IssueRefreshTokenPort.IssuedRefreshToken refresh = issueRefreshTokenPort.issueRefreshToken(
				credential.getId(),
				Role.CORPORATION
		);

		saveRefreshTokenPort.save(new RefreshToken(
				credential.getId(),
				Role.CORPORATION,
				refresh.token(),
				LocalDateTime.now().plus(refresh.ttl())
		));

		saveAccessTokenPort.save(
				credential.getId(),
				Role.CORPORATION,
				access.token(),
				access.ttl()
		);

		return new SigninCorporationResult(
				access.token(),
				refresh.token(),
				refresh.ttl()
		);
	}
}
