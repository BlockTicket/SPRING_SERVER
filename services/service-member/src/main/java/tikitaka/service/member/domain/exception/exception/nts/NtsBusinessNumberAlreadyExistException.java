package tikitaka.service.member.domain.exception.exception.nts;

import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.domain.exception.status_code.NtsVerificationStatusCode;

public class NtsBusinessNumberAlreadyExistException extends CommonException {

	public NtsBusinessNumberAlreadyExistException() { super(NtsVerificationStatusCode.NTS_BUSINESS_NUMBER_ALREADY_EXISTS); }
}
