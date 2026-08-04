package tikitaka.service.auth.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.auth.domain.exception.status_code.AuthStatusCode;

public class AccountNotFoundException extends CommonException {

    public AccountNotFoundException() {

        super(AuthStatusCode.ACCOUNT_NOT_FOUND);
    }
}
