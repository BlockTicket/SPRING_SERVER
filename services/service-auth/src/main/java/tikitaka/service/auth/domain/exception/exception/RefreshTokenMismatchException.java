package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class RefreshTokenMismatchException extends CommonException {

	public RefreshTokenMismatchException() { super(AuthStatusCode.REFRESH_TOKEN_MISMATCH); }
}
