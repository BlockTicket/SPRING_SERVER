package tikitaka.service.auth.application.port.out;

import tikitaka.service.auth.domain.MemberCredential;

public interface LoadMemberCredentialPort {

    MemberCredential loadMemberByUsername(
            String username
    );
}
