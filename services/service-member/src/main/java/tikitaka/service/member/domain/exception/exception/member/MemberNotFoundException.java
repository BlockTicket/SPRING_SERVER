package tikitaka.service.member.domain.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class MemberNotFoundException extends CommonException {

	public MemberNotFoundException() { super(MemberStatusCode.USER_NOT_FOUND); }
}
