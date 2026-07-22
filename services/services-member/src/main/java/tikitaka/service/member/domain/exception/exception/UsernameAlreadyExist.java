package tikitaka.service.member.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class UsernameAlreadyExist extends CommonException {

	public UsernameAlreadyExist() { super(MemberStatusCode.USERNAME_ALREADY_EXIST); }
}
