package tikitaka.service.member.adapter.in.web.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.adapter.in.web.exception.status_code.MemberStatusCode;

public class EmailAlreadyExistException extends CommonException {

	public EmailAlreadyExistException() { super(MemberStatusCode.EMAIL_ALREADY_EXIST); }
}
