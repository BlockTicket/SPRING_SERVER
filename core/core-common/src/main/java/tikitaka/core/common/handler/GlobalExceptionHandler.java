package tikitaka.core.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.core.common.exception.CommonException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<CommonResponse<Void>> exceptionHandler(
			CommonException commonException
	) {

		return CommonResponse
				.error(commonException)
				.toResponseEntity();
	}
}
