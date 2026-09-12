package tikitaka.service.term.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum TermStatusCode implements StatusCode {

	TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "이용약관을 찾을 수 없습니다.", "TERM_NOT_FOUND");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
