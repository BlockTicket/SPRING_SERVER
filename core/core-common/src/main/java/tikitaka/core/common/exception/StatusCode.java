package tikitaka.core.common.exception;

import org.springframework.http.HttpStatus;

public interface StatusCode {

	String getCode();

	String getMessage();

	HttpStatus getHttpStatus();
}
