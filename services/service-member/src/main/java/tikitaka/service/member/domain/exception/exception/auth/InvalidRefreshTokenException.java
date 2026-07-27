package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class InvalidRefreshTokenException extends CommonException {

    public InvalidRefreshTokenException() {

		super(AuthStatusCode.INVALID_REFRESH_TOKEN);
	}
}