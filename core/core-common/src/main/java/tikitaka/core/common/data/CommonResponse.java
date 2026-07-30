package tikitaka.core.common.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tikitaka.core.common.exception.CommonException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T>(
		int httpStatus,
		String message,
		String code,
		T data
) {

	public static <T> CommonResponse<T> of(
			HttpStatus httpStatus,
			String message,
			String code,
			T data
	) {

		return new CommonResponse<>(
				httpStatus.value(),
				message,
				code,
				data
		);
	}

	public static <T> CommonResponse<T> ok(
			String message,
			T data
	) {

		return CommonResponse.of(
				HttpStatus.OK,
				message,
				null,
				data
		);
	}

	public static CommonResponse<Void> ok(
			String message
	) {

		return CommonResponse.of(
				HttpStatus.OK,
				message,
				null,
				null
		);
	}

	public static <T> CommonResponse<T> health(
			T data
	) {

		return CommonResponse.of(
				HttpStatus.OK,
				null,
				null,
				data
		);
	}

	public static CommonResponse<Void> error(
			CommonException commonException
	) {

		return CommonResponse.of(
				commonException.statusCode.getHttpStatus(),
				commonException.statusCode.getMessage(),
				commonException.statusCode.getCode(),
				null
		);
	}

	public ResponseEntity<CommonResponse<T>> toResponseEntity() {

		return ResponseEntity
				.status(httpStatus)
				.body(this);
	}
}