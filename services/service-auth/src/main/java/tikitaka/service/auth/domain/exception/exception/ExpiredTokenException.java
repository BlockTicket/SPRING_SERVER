package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class ExpiredTokenException extends CommonException {

	public ExpiredTokenException() { super(AuthStatusCode.EXPIRED_TOKEN); }
}
