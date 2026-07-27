package tikitaka.service.member.domain.exception.exception.auth;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.AuthStatusCode;

public class AccountNotFoundException extends CommonException {

    public AccountNotFoundException() {

        super(AuthStatusCode.ACCOUNT_NOT_FOUND);
    }
}