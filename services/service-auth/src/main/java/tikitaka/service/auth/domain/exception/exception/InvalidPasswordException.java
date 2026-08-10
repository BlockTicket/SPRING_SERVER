package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class InvalidPasswordException extends CommonException {

	public InvalidPasswordException() { super(AuthStatusCode.INVALID_PASSWORD); }
}
