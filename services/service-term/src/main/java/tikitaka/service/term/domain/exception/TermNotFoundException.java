package tikitaka.service.term.domain.exception;

import tikitaka.core.common.exception.CommonException;

public class TermNotFoundException extends CommonException {

	public TermNotFoundException() {

		super(TermStatusCode.TERM_NOT_FOUND);
	}
}
