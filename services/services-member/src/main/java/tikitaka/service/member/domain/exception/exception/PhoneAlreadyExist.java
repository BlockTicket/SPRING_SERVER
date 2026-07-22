package tikitaka.service.member.domain.exception.exception;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class PhoneAlreadyExist extends CommonException {

	public PhoneAlreadyExist() { super(MemberStatusCode.PHONE_ALREADY_EXIST); }
}
