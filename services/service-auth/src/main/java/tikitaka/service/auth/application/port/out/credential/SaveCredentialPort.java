package tikitaka.service.auth.application.port.out.credential;

import tikitaka.service.auth.domain.credential.Credential;

public interface SaveCredentialPort {

	void save(Credential credential);
}
