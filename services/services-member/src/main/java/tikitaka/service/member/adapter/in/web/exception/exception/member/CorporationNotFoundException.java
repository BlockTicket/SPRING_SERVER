package tikitaka.service.member.adapter.in.web.exception.exception.member;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.adapter.in.web.exception.status_code.MemberStatusCode;

public class CorporationNotFoundException extends CommonException {

	public CorporationNotFoundException() {
		super(MemberStatusCode.CORPORATION_NOT_FOUND);
	}
}
