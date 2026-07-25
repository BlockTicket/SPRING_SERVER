package tikitaka.service.member.domain.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class PhoneAlreadyExistException extends CommonException {

	public PhoneAlreadyExistException() { super(MemberStatusCode.PHONE_ALREADY_EXIST); }
}
