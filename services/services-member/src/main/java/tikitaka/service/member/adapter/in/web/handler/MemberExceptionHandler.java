package tikitaka.service.member.adapter.in.web.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.core.common.exception.CommonException;
import tikitaka.core.common.handler.GlobalExceptionHandler;
import tikitaka.service.member.domain.exception.exception.EmailAlreadyExist;
import tikitaka.service.member.domain.exception.exception.PhoneAlreadyExist;
import tikitaka.service.member.domain.exception.exception.UsernameAlreadyExist;

@RestControllerAdvice
public class MemberExceptionHandler implements GlobalExceptionHandler {

	@Override
	@ExceptionHandler(CommonException.class)
	public ResponseEntity<CommonResponse<Void>> exceptionHandler(
			CommonException commonException
	) {

		return CommonResponse
				.error(commonException)
				.toResponseEntity();
	}

	@ExceptionHandler(UsernameAlreadyExist.class)
	public ResponseEntity<CommonResponse<Void>> usernameHandler(
			UsernameAlreadyExist usernameAlreadyExist
	) {

		return CommonResponse
				.error(usernameAlreadyExist)
				.toResponseEntity();
	}

	@ExceptionHandler(EmailAlreadyExist.class)
	public ResponseEntity<CommonResponse<Void>> emailHandler(
			EmailAlreadyExist emailAlreadyExist
	) {

		return CommonResponse
				.error(emailAlreadyExist)
				.toResponseEntity();
	}

	@ExceptionHandler(PhoneAlreadyExist.class)
	public ResponseEntity<CommonResponse<Void>> phoneHandler(
			PhoneAlreadyExist phoneAlreadyExist
	) {

		return CommonResponse
				.error(phoneAlreadyExist)
				.toResponseEntity();
	}
}