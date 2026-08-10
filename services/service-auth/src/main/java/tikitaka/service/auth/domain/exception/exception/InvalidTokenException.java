package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class InvalidTokenException extends CommonException {

	public InvalidTokenException() { super(AuthStatusCode.INVALID_TOKEN); }
}
