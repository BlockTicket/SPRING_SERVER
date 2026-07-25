package tikitaka.service.member.domain.exception.exception.nts;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.NtsVerificationStatusCode;

public class NtsVerificationException extends CommonException {

	public NtsVerificationException() { super(NtsVerificationStatusCode.CANNOT_VERIFY_NTS_BUSINESS); }
}
