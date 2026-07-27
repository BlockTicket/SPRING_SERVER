package tikitaka.service.member.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.auth.LoginRequest;
import tikitaka.service.member.adapter.in.web.data.response.auth.AccessTokenResponse;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginTokenResponse;
import tikitaka.service.member.application.port.in.auth.LoginUseCase;
import tikitaka.service.member.application.port.in.auth.LogoutCommand;
import tikitaka.service.member.application.port.in.auth.LogoutUseCase;
import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenCommand;
import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenUseCase;
import tikitaka.service.member.domain.auth.AccountType;
import tikitaka.service.member.domain.auth.TokenPair;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
	private static final String BEARER_PREFIX = "Bearer ";

	private final LoginUseCase loginUseCase;
	private final LogoutUseCase logoutUseCase;
	private final ReissueAccessTokenUseCase reissueAccessTokenUseCase;
	private final RefreshTokenCookieFactory refreshTokenCookieFactory;

	@PostMapping("/member/login")
	public ResponseEntity<CommonResponse<LoginTokenResponse>> loginMember(
			@Valid @RequestBody LoginRequest loginRequest
	) {

		return login(loginRequest, AccountType.MEMBER);
	}

	@PostMapping("/corporation/login")
	public ResponseEntity<CommonResponse<LoginTokenResponse>> loginCorporation(
			@Valid @RequestBody LoginRequest loginRequest
	) {

		return login(loginRequest, AccountType.CORPORATION);
	}

	@PostMapping("/member/logout")
	public ResponseEntity<CommonResponse<Void>> logoutMember(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
			@CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return logout(authorization, refreshToken, AccountType.MEMBER);
	}

	@PostMapping("/corporation/logout")
	public ResponseEntity<CommonResponse<Void>> logoutCorporation(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
			@CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return logout(authorization, refreshToken, AccountType.CORPORATION);
	}

	@PostMapping("/member/refresh")
	public ResponseEntity<CommonResponse<AccessTokenResponse>> reissueMemberAccessToken(
			@CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return reissueAccessToken(refreshToken, AccountType.MEMBER);
	}

	@PostMapping("/corporation/refresh")
	public ResponseEntity<CommonResponse<AccessTokenResponse>> reissueCorporationAccessToken(
			@CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return reissueAccessToken(refreshToken, AccountType.CORPORATION);
	}

	private ResponseEntity<CommonResponse<LoginTokenResponse>> login(
			LoginRequest loginRequest,
			AccountType accountType
	) {

		TokenPair tokenPair = loginUseCase.login(loginRequest.toCommand(accountType));

		return ResponseEntity.ok()
				.header(
						HttpHeaders.SET_COOKIE,
						refreshTokenCookieFactory.create(
								tokenPair.refreshToken(),
								tokenPair.refreshTokenExpirationSeconds()
						).toString()
				)
				.body(CommonResponse.ok("로그인 되었습니다.", LoginTokenResponse.from(tokenPair)));
	}

	private ResponseEntity<CommonResponse<Void>> logout(
			String authorization,
			String refreshToken,
			AccountType accountType
	) {

		logoutUseCase.logout(new LogoutCommand(
				extractAccessToken(authorization),
				refreshToken,
				accountType
		));

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, refreshTokenCookieFactory.delete().toString())
				.body(CommonResponse.ok("로그아웃 되었습니다."));
	}

	private ResponseEntity<CommonResponse<AccessTokenResponse>> reissueAccessToken(
			String refreshToken,
			AccountType accountType
	) {

		if (refreshToken == null || refreshToken.isBlank()) throw new InvalidTokenException();

		String accessToken = reissueAccessTokenUseCase.reissueAccessToken(
				new ReissueAccessTokenCommand(refreshToken, accountType)
		);

		return CommonResponse.ok("액세스 토큰이 재발급되었습니다.", new AccessTokenResponse(accessToken))
				.toResponseEntity();
	}

	private String extractAccessToken(String authorization) {

		if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {

			throw new InvalidTokenException();
		}

		return authorization.substring(BEARER_PREFIX.length());
	}
}
