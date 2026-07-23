package tikitaka.service.member.adapter.in.web.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.core.common.exception.CommonException;
import tikitaka.core.common.handler.GlobalExceptionHandler;
import tikitaka.service.member.adapter.in.web.exception.exception.member.EmailAlreadyExistException;
import tikitaka.service.member.adapter.in.web.exception.exception.member.PhoneAlreadyExistException;
import tikitaka.service.member.adapter.in.web.exception.exception.member.UsernameAlreadyExistException;

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

	@ExceptionHandler(UsernameAlreadyExistException.class)
	public ResponseEntity<CommonResponse<Void>> usernameHandler(
			UsernameAlreadyExistException usernameAlreadyExistException
	) {

		return CommonResponse
				.error(usernameAlreadyExistException)
				.toResponseEntity();
	}

	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<CommonResponse<Void>> emailHandler(
			EmailAlreadyExistException emailAlreadyExistException
	) {

		return CommonResponse
				.error(emailAlreadyExistException)
				.toResponseEntity();
	}

	@ExceptionHandler(PhoneAlreadyExistException.class)
	public ResponseEntity<CommonResponse<Void>> phoneHandler(
			PhoneAlreadyExistException phoneAlreadyExistException
	) {

		return CommonResponse
				.error(phoneAlreadyExistException)
				.toResponseEntity();
	}
}