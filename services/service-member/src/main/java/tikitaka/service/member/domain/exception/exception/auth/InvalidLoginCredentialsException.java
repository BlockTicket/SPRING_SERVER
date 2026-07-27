package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class InvalidLoginCredentialsException extends CommonException {

	public InvalidLoginCredentialsException() {

		super(AuthStatusCode.INVALID_LOGIN_CREDENTIALS);
	}
}
