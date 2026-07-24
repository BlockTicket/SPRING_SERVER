package tikitaka.service.member.domain.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class EmailAlreadyExistException extends CommonException {

	public EmailAlreadyExistException() { super(MemberStatusCode.EMAIL_ALREADY_EXIST); }
}
