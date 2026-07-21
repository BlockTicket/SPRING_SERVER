package tikitaka.core.common.handler;

import org.springframework.http.ResponseEntity;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.core.common.exception.CommonException;

public interface GlobalExceptionHandler {

	ResponseEntity<CommonResponse<Void>> exceptionHandler(CommonException commonException);
}
