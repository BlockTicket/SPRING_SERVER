package tikitaka.service.auth.adapter.out.persistence.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.out.credential.FindCredentialPort;
import tikitaka.service.auth.application.port.out.credential.SaveCredentialPort;
import tikitaka.service.auth.domain.credential.Credential;
import tikitaka.service.auth.domain.role.Role;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CredentialPersistenceAdapter implements SaveCredentialPort, FindCredentialPort {

	private final CredentialJpaRepository credentialJpaRepository;

	@Override
	public void save(
			Credential credential
	) {

		credentialJpaRepository.save(CredentialJpaEntity.builder()
				.id(credential.getId())
				.username(credential.getUsername())
				.password(credential.getPassword())
				.role(credential.getRole())
				.build()
		);
	}

	@Override
	public Optional<Credential> findByUsernameAndRole(
			String username,
			Role role
	) {

		return credentialJpaRepository
				.findByUsernameAndRole(username, role)
				.map(entity -> new Credential(
						entity.getId(),
						entity.getUsername(),
						entity.getPassword(),
						entity.getRole()
				));
	}
}
