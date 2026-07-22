package tikitaka.service.member.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class EmailAlreadyExist extends CommonException {

	public EmailAlreadyExist() { super(MemberStatusCode.EMAIL_ALREADY_EXIST); }
}
