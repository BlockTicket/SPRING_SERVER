package tikitaka.service.member.domain.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum NtsVerificationStatusCode implements StatusCode {

	NTS_BODY_HAS_NO_DATA(HttpStatus.NO_CONTENT, "본문이 없습니다.", "NTS_BODY_HAS_NO_DATA"),
	CANNOT_VERIFY_NTS_BUSINESS(HttpStatus.BAD_REQUEST, "사업자를 인증할 수 없습니다.", "CANNOT_VERIFY_NTS_BUSINESS"),
	NTS_BUSINESS_NUMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 사업자 번호 입니다.", "NTS_BUSINESS_NUMBER_ALREADY_EXISTS");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
