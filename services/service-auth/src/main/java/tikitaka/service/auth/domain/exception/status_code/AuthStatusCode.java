package tikitaka.service.auth.domain.exception.status_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum AuthStatusCode implements StatusCode {

    ACCOUNT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "계정을 찾을 수 없습니다.",
            "ACCOUNT_NOT_FOUND"
    ),

    INVALID_PASSWORD(
            HttpStatus.UNAUTHORIZED,
            "비밀번호가 일치하지 않습니다.",
            "INVALID_PASSWORD"
    ),

    INVALID_ACCESS_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "유효하지 않은 Access Token 입니다.",
            "INVALID_ACCESS_TOKEN"
    ),

    INVALID_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "유효하지 않은 Refresh Token 입니다.",
            "INVALID_REFRESH_TOKEN"
    ),

    EXPIRED_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "만료된 Refresh Token 입니다.",
            "EXPIRED_REFRESH_TOKEN"
    );

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
