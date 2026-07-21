package tikitaka.core.common.exception;

public class CommonException extends RuntimeException {

	public final StatusCode statusCode;

	public CommonException(StatusCode statusCode) {

		super(statusCode.getMessage());
		this.statusCode = statusCode;
	}

	public StatusCode getStatusCode() {

		return statusCode;
	}
}
