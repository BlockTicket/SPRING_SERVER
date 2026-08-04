package tikitaka.service.auth.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.out.LoadMemberCredentialPort;
import tikitaka.service.auth.domain.MemberCredential;
import tikitaka.service.auth.domain.exception.exception.AccountNotFoundException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCredentialPersistenceAdapter implements LoadMemberCredentialPort {

    private final MemberCredentialJpaRepository memberCredentialJpaRepository;

    @Override
    public MemberCredential loadMemberByUsername(
            String username
    ) {

        MemberCredentialJpaEntity entity =
                memberCredentialJpaRepository.findByUsername(username)
                        .orElseThrow(AccountNotFoundException::new);

        return new MemberCredential(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword()
        );
    }
}
