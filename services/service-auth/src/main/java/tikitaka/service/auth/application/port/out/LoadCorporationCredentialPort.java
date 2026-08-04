package tikitaka.service.auth.application.port.out;

import tikitaka.service.auth.domain.CorporationCredential;

public interface LoadCorporationCredentialPort {

    CorporationCredential loadCorporationByUsername(
            String username
    );
}
