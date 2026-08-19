package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class RefreshTokenNotFoundException extends CommonException {

	public RefreshTokenNotFoundException() { super(AuthStatusCode.REFRESH_TOKEN_NOT_FOUND); }
}
