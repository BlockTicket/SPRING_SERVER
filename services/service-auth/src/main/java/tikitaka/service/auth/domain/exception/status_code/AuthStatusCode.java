package tikitaka.service.auth.domain.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum AuthStatusCode implements StatusCode {

	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다.", "REFRESH_TOKEN_NOT_FOUND"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", "INVALID_TOKEN"),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", "EXPIRED_TOKEN"),
	REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다.", "REFRESH_TOKEN_MISMATCH"),
	ACCESS_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, "액세스 토큰이 필요합니다.", "ACCESS_TOKEN_REQUIRED"),
	REFRESH_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 필요합니다.", "REFRESH_TOKEN_REQUIRED"),
	CREDENTIAL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다.", "CREDENTIAL_NOT_FOUND"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", "INVALID_PASSWORD");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
