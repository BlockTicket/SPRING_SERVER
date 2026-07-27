package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class InvalidPasswordException extends CommonException {

    public InvalidPasswordException() {

        super(AuthStatusCode.INVALID_PASSWORD);
    }
}