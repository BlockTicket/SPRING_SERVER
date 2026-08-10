package tikitaka.service.auth.application.service.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.in.credential.RegisterCredentialCommand;
import tikitaka.service.auth.application.port.in.credential.RegisterCredentialUseCase;
import tikitaka.service.auth.application.port.out.credential.SaveCredentialPort;
import tikitaka.service.auth.domain.credential.Credential;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RegisterCredentialService implements RegisterCredentialUseCase {

	private final SaveCredentialPort saveCredentialPort;

	@Override
	public void register(
			RegisterCredentialCommand registerCredentialCommand
	) {

		saveCredentialPort.save(new Credential(
				registerCredentialCommand.id(),
				registerCredentialCommand.username(),
				registerCredentialCommand.passwordHash(),
				registerCredentialCommand.role()
		));
	}
}
