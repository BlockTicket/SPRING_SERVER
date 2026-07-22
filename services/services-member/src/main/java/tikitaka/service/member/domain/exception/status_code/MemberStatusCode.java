package tikitaka.service.member.domain.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum MemberStatusCode implements StatusCode {

	USERNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_EXIST", "중복된 사용자명 입니다."),
	EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_EXIST", "해당 이메일로 이미 가입된 계정이 존재합니다."),
	PHONE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "PHONE_ALREADY_EXIST", "해당 전화번호로 이미 가입된 계정이 존재합니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
