package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class InvalidRefreshTokenException extends CommonException {

    public InvalidRefreshTokenException() {

        super(AuthStatusCode.INVALID_REFRESH_TOKEN);
    }
}
