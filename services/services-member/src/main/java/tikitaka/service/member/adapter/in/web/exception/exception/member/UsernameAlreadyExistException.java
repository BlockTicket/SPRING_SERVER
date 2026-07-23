package tikitaka.service.member.adapter.in.web.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.adapter.in.web.exception.status_code.MemberStatusCode;

public class UsernameAlreadyExistException extends CommonException {

	public UsernameAlreadyExistException() { super(MemberStatusCode.USERNAME_ALREADY_EXIST); }
}
