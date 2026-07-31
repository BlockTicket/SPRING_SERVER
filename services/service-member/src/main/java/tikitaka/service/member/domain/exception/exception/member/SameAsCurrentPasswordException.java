package tikitaka.service.member.domain.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class SameAsCurrentPasswordException extends CommonException {

	public SameAsCurrentPasswordException() { super(MemberStatusCode.INVALID_PASSWORD); }
}
