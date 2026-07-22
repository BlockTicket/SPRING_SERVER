package tikitaka.core.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.core.common.exception.CommonException;

public interface GlobalExceptionHandler {

	ResponseEntity<CommonResponse<Void>> exceptionHandler(CommonException commonException);
}
