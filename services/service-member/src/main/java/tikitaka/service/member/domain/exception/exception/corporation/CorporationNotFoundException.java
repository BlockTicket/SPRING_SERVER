package tikitaka.service.member.domain.exception.exception.corporation;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.MemberStatusCode;

public class CorporationNotFoundException extends CommonException {

	public CorporationNotFoundException() { super(MemberStatusCode.CORPORATION_NOT_FOUND); }
}
