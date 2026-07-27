package tikitaka.service.member.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.TokenPair;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

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
			@AuthenticationPrincipal AuthTokenClaims accessTokenClaims,
			@CookieValue(value = RefreshTokenCookieFactory.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return logout(accessTokenClaims, refreshToken, AccountType.MEMBER);
	}

	@PostMapping("/corporation/logout")
	public ResponseEntity<CommonResponse<Void>> logoutCorporation(
			@AuthenticationPrincipal AuthTokenClaims accessTokenClaims,
			@CookieValue(value = RefreshTokenCookieFactory.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return logout(accessTokenClaims, refreshToken, AccountType.CORPORATION);
	}

	@PostMapping("/member/refresh")
	public ResponseEntity<CommonResponse<AccessTokenResponse>> reissueMemberAccessToken(
			@CookieValue(value = RefreshTokenCookieFactory.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		return reissueAccessToken(refreshToken, AccountType.MEMBER);
	}

	@PostMapping("/corporation/refresh")
	public ResponseEntity<CommonResponse<AccessTokenResponse>> reissueCorporationAccessToken(
			@CookieValue(value = RefreshTokenCookieFactory.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
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
			AuthTokenClaims accessTokenClaims,
			String refreshToken,
			AccountType accountType
	) {

		if (accessTokenClaims == null) throw new InvalidTokenException();

		logoutUseCase.logout(new LogoutCommand(
				accessTokenClaims,
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
}
