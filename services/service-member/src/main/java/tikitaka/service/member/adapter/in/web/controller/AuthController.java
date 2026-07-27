package tikitaka.service.member.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.auth.LoginRequest;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;
import tikitaka.service.member.adapter.in.web.security.TokenHeaders;
import tikitaka.service.member.application.port.in.auth.*;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

import java.util.UUID;

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
	public ResponseEntity<CommonResponse<Void>> memberTokenRefresh(
			@RequestHeader(value = TokenHeaders.REFRESH_TOKEN, required = false) String refreshTokenHeader
	) {

		LoginResponse loginResponse = memberTokenRefreshUseCase.memberTokenRefresh(
				new MemberRefreshTokenCommand(getRefreshToken(refreshTokenHeader))
		);

		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, TokenHeaders.bearer(loginResponse.accessToken()))
				.header(TokenHeaders.REFRESH_TOKEN, TokenHeaders.bearer(loginResponse.refreshToken()))
				.body(CommonResponse.ok("회원 토큰이 재발급되었습니다."));
	}

	@PostMapping("/corporation/refresh")
	public ResponseEntity<CommonResponse<Void>> corporationTokenRefresh(
			@RequestHeader(value = TokenHeaders.REFRESH_TOKEN, required = false) String refreshTokenHeader
	) {

		LoginResponse loginResponse = corporationTokenRefreshUseCase.corporationTokenRefresh(
				new CorporationRefreshTokenCommand(getRefreshToken(refreshTokenHeader))
		);

		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, TokenHeaders.bearer(loginResponse.accessToken()))
				.header(TokenHeaders.REFRESH_TOKEN, TokenHeaders.bearer(loginResponse.refreshToken()))
				.body(CommonResponse.ok("법인 토큰이 재발급되었습니다."));
	}

	@PostMapping("/member/logout")
	public ResponseEntity<CommonResponse<Void>> memberLogout(
			@AuthenticationPrincipal UUID memberId
	) {

		memberLogoutUseCase.memberLogout(
				new MemberLogoutCommand(memberId)
		);

		return CommonResponse.ok("회원 로그아웃 되었습니다.").toResponseEntity();
	}

	@PostMapping("/corporation/logout")
	public ResponseEntity<CommonResponse<Void>> corporationLogout(
			@AuthenticationPrincipal UUID corporationId
	) {

		corporationLogoutUseCase.corporationLogout(
				new CorporationLogoutCommand(corporationId)
		);

		return CommonResponse.ok("법인 로그아웃 되었습니다.").toResponseEntity();
	}

	private String getRefreshToken(String refreshTokenHeader) {

		if (!TokenHeaders.hasBearerPrefix(refreshTokenHeader)) {

			throw new InvalidRefreshTokenException();
		}

		return TokenHeaders.withoutBearerPrefix(refreshTokenHeader);
	}
}
