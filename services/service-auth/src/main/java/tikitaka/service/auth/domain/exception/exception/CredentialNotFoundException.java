package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class CredentialNotFoundException extends CommonException {

	public CredentialNotFoundException() { super(AuthStatusCode.CREDENTIAL_NOT_FOUND); }
}
