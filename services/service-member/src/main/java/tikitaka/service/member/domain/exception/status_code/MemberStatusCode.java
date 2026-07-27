package tikitaka.service.member.domain.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum MemberStatusCode implements StatusCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", "USER_NOT_FOUND"),
	USERNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "중복된 사용자명 입니다.", "USERNAME_ALREADY_EXIST"),
	EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 이메일로 이미 가입된 계정이 존재합니다.", "EMAIL_ALREADY_EXIST"),
	PHONE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 전화번호로 이미 가입된 계정이 존재합니다.","PHONE_ALREADY_EXIST"),
	SAME_AS_CURRENT_USERNAME(HttpStatus.BAD_REQUEST, "현재 사용중인 사용자명으로 변경할 수 없습니다.", "SAME_AS_CURRENT_USERNAME"),

	CORPORATION_NOT_FOUND(HttpStatus.NOT_FOUND, "법인을 찾을 수 없습니다.","CORPORATION_NOT_FOUND");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
