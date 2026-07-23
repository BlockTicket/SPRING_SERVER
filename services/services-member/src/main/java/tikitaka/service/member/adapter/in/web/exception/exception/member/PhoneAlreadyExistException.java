package tikitaka.service.member.adapter.in.web.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.adapter.in.web.exception.status_code.MemberStatusCode;

public class PhoneAlreadyExistException extends CommonException {

	public PhoneAlreadyExistException() { super(MemberStatusCode.PHONE_ALREADY_EXIST); }
}
