package tikitaka.service.member.adapter.in.web.exception.exception.nts;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.adapter.in.web.exception.status_code.NtsVerificationStatusCode;

public class NtsVerificationException extends CommonException {

	public NtsVerificationException() { super(NtsVerificationStatusCode.CANNOT_VERIFY_NTS_BUSINESS); }
}
