package tikitaka.service.member.adapter.in.web.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum NtsVerificationStatusCode implements StatusCode {

	NTS_BODY_HAS_NO_DATA(HttpStatus.NO_CONTENT, "NTS_BODY_HAS_NO_DATA", "본문이 없습니다."),
	CANNOT_VERIFY_NTS_BUSINESS(HttpStatus.BAD_REQUEST, "CANNOT_VERIFY_NTS_BUSINESS", "사업자를 인증할 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
