package tikitaka.service.auth.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.out.LoadCorporationCredentialPort;
import tikitaka.service.auth.domain.CorporationCredential;
import tikitaka.service.auth.domain.exception.exception.AccountNotFoundException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporationCredentialPersistenceAdapter implements LoadCorporationCredentialPort {

    private final CorporationCredentialJpaRepository corporationCredentialJpaRepository;

    @Override
    public CorporationCredential loadCorporationByUsername(
            String username
    ) {

        CorporationCredentialJpaEntity entity =
                corporationCredentialJpaRepository.findByUsername(username)
                        .orElseThrow(AccountNotFoundException::new);

        return new CorporationCredential(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword()
        );
    }
}
