package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class ExpiredRefreshTokenException extends CommonException {

	public ExpiredRefreshTokenException() {

		super(AuthStatusCode.EXPIRED_REFRESH_TOKEN);
	}
}