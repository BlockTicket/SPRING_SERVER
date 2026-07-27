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
import tikitaka.service.member.application.port.in.auth.LogoutCommand;
import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenCommand;
import tikitaka.service.member.application.port.in.auth.member.MemberLoginUseCase;
import tikitaka.service.member.application.port.in.auth.member.MemberLogoutUseCase;
import tikitaka.service.member.application.port.in.auth.member.MemberReissueAccessTokenUseCase;
import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.TokenPair;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/member")
public class MemberAuthController {

	private final MemberLoginUseCase loginUseCase;
	private final MemberLogoutUseCase logoutUseCase;
	private final MemberReissueAccessTokenUseCase reissueAccessTokenUseCase;
	private final RefreshTokenCookieFactory refreshTokenCookieFactory;

	@PostMapping("/login")
	public ResponseEntity<CommonResponse<LoginTokenResponse>> login(
			@Valid @RequestBody LoginRequest loginRequest
	) {

		TokenPair tokenPair = loginUseCase.login(loginRequest.toCommand());

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

	@PostMapping("/logout")
	public ResponseEntity<CommonResponse<Void>> logout(
			@AuthenticationPrincipal AuthTokenClaims accessTokenClaims,
			@CookieValue(value = RefreshTokenCookieFactory.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		if (accessTokenClaims == null) throw new InvalidTokenException();

		logoutUseCase.logout(new LogoutCommand(
				accessTokenClaims,
				refreshToken
		));

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, refreshTokenCookieFactory.delete().toString())
				.body(CommonResponse.ok("로그아웃 되었습니다."));
	}

	@PostMapping("/refresh")
	public ResponseEntity<CommonResponse<AccessTokenResponse>> reissueAccessToken(
			@CookieValue(value = RefreshTokenCookieFactory.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		if (refreshToken == null || refreshToken.isBlank()) throw new InvalidTokenException();

		String accessToken = reissueAccessTokenUseCase.reissueAccessToken(
				new ReissueAccessTokenCommand(refreshToken)
		);

		return CommonResponse.ok("액세스 토큰이 재발급되었습니다.", new AccessTokenResponse(accessToken))
				.toResponseEntity();
	}
}
