package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class InvalidCredentialsException extends CommonException {

	public InvalidCredentialsException() { super(AuthStatusCode.INVALID_CREDENTIALS); }
}
