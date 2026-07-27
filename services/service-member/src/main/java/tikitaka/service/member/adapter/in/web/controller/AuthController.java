package tikitaka.service.member.adapter.in.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.auth.LoginRequest;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;
import tikitaka.service.member.application.port.in.auth.*;
import tikitaka.service.member.domain.exception.exception.auth.InvalidAccessTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private static final String BEARER_PREFIX = "Bearer ";
	private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
	private static final String ACCESS_TOKEN_COOKIE = "accessToken";
	private static final String REFRESH_TOKEN_COOKIE = "refreshToken";

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
			@RequestHeader(value = REFRESH_TOKEN_HEADER, required = false) String refreshTokenHeader
	) {

		LoginResponse loginResponse = memberTokenRefreshUseCase.memberTokenRefresh(
				new MemberRefreshTokenCommand(getRefreshToken(refreshTokenHeader))
		);

		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + loginResponse.accessToken())
				.header(REFRESH_TOKEN_HEADER, BEARER_PREFIX + loginResponse.refreshToken())
				.body(CommonResponse.ok("회원 토큰이 재발급되었습니다."));
	}

	@PostMapping("/corporation/refresh")
	public ResponseEntity<CommonResponse<Void>> corporationTokenRefresh(
			@RequestHeader(value = REFRESH_TOKEN_HEADER, required = false) String refreshTokenHeader
	) {

		LoginResponse loginResponse = corporationTokenRefreshUseCase.corporationTokenRefresh(
				new CorporationRefreshTokenCommand(getRefreshToken(refreshTokenHeader))
		);

		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + loginResponse.accessToken())
				.header(REFRESH_TOKEN_HEADER, BEARER_PREFIX + loginResponse.refreshToken())
				.body(CommonResponse.ok("법인 토큰이 재발급되었습니다."));
	}

	@PostMapping("/member/logout")
	public ResponseEntity<CommonResponse<Void>> memberLogout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse
	) {

		memberLogoutUseCase.memberLogout(
				new MemberLogoutCommand(resolveAccessToken(httpServletRequest))
		);

		deleteTokenCookie(ACCESS_TOKEN_COOKIE, httpServletResponse);
		deleteTokenCookie(REFRESH_TOKEN_COOKIE, httpServletResponse);

		return CommonResponse.ok("회원 로그아웃 되었습니다.").toResponseEntity();
	}

	@PostMapping("/corporation/logout")
	public ResponseEntity<CommonResponse<Void>> corporationLogout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse
	) {

		corporationLogoutUseCase.corporationLogout(
				new CorporationLogoutCommand(resolveAccessToken(httpServletRequest))
		);

		deleteTokenCookie(ACCESS_TOKEN_COOKIE, httpServletResponse);
		deleteTokenCookie(REFRESH_TOKEN_COOKIE, httpServletResponse);

		return CommonResponse.ok("법인 로그아웃 되었습니다.").toResponseEntity();
	}

	private String getAccessToken(String authorizationHeader) {

		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {

			throw new InvalidAccessTokenException();
		}

		return authorizationHeader.substring(BEARER_PREFIX.length());
	}

	private String resolveAccessToken(HttpServletRequest httpServletRequest) {

		String accessToken = extractTokenFromCookie(
				ACCESS_TOKEN_COOKIE,
				httpServletRequest
		);

		if (accessToken != null && !accessToken.isBlank()) {

			return accessToken.startsWith(BEARER_PREFIX)
					? accessToken.substring(BEARER_PREFIX.length())
					: accessToken;
		}

		return getAccessToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
	}

	private String extractTokenFromCookie(
			String cookieName,
			HttpServletRequest httpServletRequest
	) {

		Cookie[] cookies = httpServletRequest.getCookies();

		if (cookies == null) {

			return null;
		}

		for (Cookie cookie : cookies) {

			if (cookieName.equals(cookie.getName())) {

				return cookie.getValue();
			}
		}

		return null;
	}

	private void deleteTokenCookie(
			String cookieName,
			HttpServletResponse httpServletResponse
	) {

		Cookie cookie = new Cookie(cookieName, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);

		httpServletResponse.addCookie(cookie);
	}

	private String getRefreshToken(String refreshTokenHeader) {

		if (refreshTokenHeader == null || !refreshTokenHeader.startsWith(BEARER_PREFIX)) {

			throw new InvalidRefreshTokenException();
		}

		return refreshTokenHeader.substring(BEARER_PREFIX.length());
	}
}
