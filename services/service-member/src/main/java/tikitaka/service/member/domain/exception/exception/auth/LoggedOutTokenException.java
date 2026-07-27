package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class LoggedOutTokenException extends CommonException {

	public LoggedOutTokenException() {

		super(AuthStatusCode.LOGGED_OUT_TOKEN);
	}
}
