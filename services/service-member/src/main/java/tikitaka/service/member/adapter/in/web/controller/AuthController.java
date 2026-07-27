package tikitaka.service.member.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.auth.LoginRequest;
import tikitaka.service.member.adapter.in.web.data.request.auth.RefreshTokenRequest;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;
import tikitaka.service.member.application.port.in.auth.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final MemberLoginUseCase memberLoginUseCase;
	private final CorporationLoginUseCase corporationLoginUseCase;
	private final MemberTokenRefreshUseCase memberTokenRefreshUseCase;
	private final CorporationTokenRefreshUseCase corporationTokenRefreshUseCase;
	private final MemberLogoutUseCase memberLogoutUseCase;
	private final CorporationLogoutUseCase corporationLogoutUseCase;

	@PostMapping("/member/signin")
	public ResponseEntity<CommonResponse<LoginResponse>> memberSignin(
			@Valid @RequestBody LoginRequest loginRequest
	) {

		LoginResponse loginResponse = memberLoginUseCase.memberLogin(
				loginRequest.toMemberLoginCommand()
		);

		return CommonResponse.ok("회원 로그인 되었습니다.", loginResponse).toResponseEntity();
	}

	@PostMapping("/corporation/signin")
	public ResponseEntity<CommonResponse<LoginResponse>> corporationSignin(
			@Valid @RequestBody LoginRequest loginRequest
	) {

		LoginResponse loginResponse = corporationLoginUseCase.corporationLogin(
				loginRequest.toCorporationLoginCommand()
		);

		return CommonResponse.ok("법인 로그인 되었습니다.", loginResponse).toResponseEntity();
	}

	@PostMapping("/member/refresh")
	public ResponseEntity<CommonResponse<LoginResponse>> memberTokenRefresh(
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequest
	) {

		LoginResponse loginResponse = memberTokenRefreshUseCase.memberTokenRefresh(
				refreshTokenRequest.toMemberRefreshTokenCommand()
		);

		return CommonResponse.ok("회원 토큰이 재발급되었습니다.", loginResponse).toResponseEntity();
	}

	@PostMapping("/corporation/refresh")
	public ResponseEntity<CommonResponse<LoginResponse>> corporationTokenRefresh(
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequest
	) {

		LoginResponse loginResponse = corporationTokenRefreshUseCase.corporationTokenRefresh(
				refreshTokenRequest.toCorporationRefreshTokenCommand()
		);

		return CommonResponse.ok("법인 토큰이 재발급되었습니다.", loginResponse).toResponseEntity();
	}

	@PostMapping("/member/logout")
	public ResponseEntity<CommonResponse<Void>> memberLogout(
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequest
	) {

		memberLogoutUseCase.memberLogout(
				refreshTokenRequest.toMemberRefreshTokenCommand()
		);

		return CommonResponse.ok("회원 로그아웃 되었습니다.").toResponseEntity();
	}

	@PostMapping("/corporation/logout")
	public ResponseEntity<CommonResponse<Void>> corporationLogout(
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequest
	) {

		corporationLogoutUseCase.corporationLogout(
				refreshTokenRequest.toCorporationRefreshTokenCommand()
		);

		return CommonResponse.ok("법인 로그아웃 되었습니다.").toResponseEntity();
	}
}
