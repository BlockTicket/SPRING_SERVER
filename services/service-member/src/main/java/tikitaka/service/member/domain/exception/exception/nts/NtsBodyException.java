package tikitaka.service.member.domain.exception.exception.nts;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.NtsVerificationStatusCode;

public class NtsBodyException extends CommonException {

	public NtsBodyException() { super(NtsVerificationStatusCode.NTS_BODY_HAS_NO_DATA); }
}
