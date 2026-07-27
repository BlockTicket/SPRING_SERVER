package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class InvalidAccessTokenException extends CommonException {

	public InvalidAccessTokenException() {

		super(AuthStatusCode.INVALID_ACCESS_TOKEN);
	}
}