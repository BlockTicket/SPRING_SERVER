package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class AccessTokenRequiredException extends CommonException {

	public AccessTokenRequiredException() { super(AuthStatusCode.ACCESS_TOKEN_REQUIRED); }
}
