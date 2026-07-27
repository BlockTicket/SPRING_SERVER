package tikitaka.service.member.domain.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum AuthStatusCode implements StatusCode {

	INVALID_LOGIN_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.", "INVALID_LOGIN_CREDENTIALS"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", "INVALID_TOKEN"),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", "EXPIRED_TOKEN"),
	INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "토큰 유형이 올바르지 않습니다.", "INVALID_TOKEN_TYPE"),
	LOGGED_OUT_TOKEN(HttpStatus.UNAUTHORIZED, "로그아웃된 토큰입니다.", "LOGGED_OUT_TOKEN");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
