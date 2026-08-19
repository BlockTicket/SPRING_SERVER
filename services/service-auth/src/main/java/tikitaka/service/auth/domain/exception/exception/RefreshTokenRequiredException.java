package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class RefreshTokenRequiredException extends CommonException {

	public RefreshTokenRequiredException() { super(AuthStatusCode.REFRESH_TOKEN_REQUIRED); }
}
