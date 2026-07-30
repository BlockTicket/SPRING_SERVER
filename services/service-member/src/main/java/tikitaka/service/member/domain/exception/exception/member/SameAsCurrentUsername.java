package tikitaka.service.member.domain.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class SameAsCurrentUsername extends CommonException {

	public SameAsCurrentUsername() {super(MemberStatusCode.SAME_AS_CURRENT_USERNAME); }
}
