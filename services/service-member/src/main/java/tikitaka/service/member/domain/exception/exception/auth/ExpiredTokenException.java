package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class ExpiredTokenException extends CommonException {

	public ExpiredTokenException() {

		super(AuthStatusCode.EXPIRED_TOKEN);
	}
}
