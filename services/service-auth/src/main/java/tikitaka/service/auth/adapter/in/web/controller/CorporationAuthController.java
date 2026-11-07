package tikitaka.service.auth.adapter.in.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.auth.adapter.in.web.cookie.RefreshTokenCookieFactory;
import tikitaka.service.auth.adapter.in.web.data.request.SigninRequest;
import tikitaka.service.auth.adapter.in.web.data.response.RefreshTokenResponse;
import tikitaka.service.auth.adapter.in.web.data.response.SigninResponse;
import tikitaka.service.auth.application.port.in.auth.RefreshTokenCommand;
import tikitaka.service.auth.application.port.in.auth.RefreshTokenUseCase;
import tikitaka.service.auth.application.port.in.auth.SignInResult;
import tikitaka.service.auth.application.port.in.auth.SignInUseCase;
import tikitaka.service.auth.application.port.in.auth.SignOutCommand;
import tikitaka.service.auth.application.port.in.auth.SignOutUseCase;
import tikitaka.service.auth.domain.exception.exception.AccessTokenRequiredException;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenRequiredException;
import tikitaka.service.auth.domain.role.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/corporation")
public class CorporationAuthController {

	private final SignInUseCase signinUseCase;
	private final SignOutUseCase signoutUseCase;
	private final RefreshTokenUseCase refreshTokenUseCase;

	@PostMapping("/signin")
	public ResponseEntity<CommonResponse<SigninResponse>> signin(
			@Valid @RequestBody SigninRequest signinRequest
	) {

		SignInResult result = signinUseCase.signin(
				signinRequest.toCommand(Role.CORPORATION)
		);

		ResponseCookie cookie = RefreshTokenCookieFactory.create(
				result.refreshToken(),
				result.refreshTokenTtl()
		);

		return ResponseEntity
				.status(200)
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(CommonResponse.ok(
						"로그인 되었습니다.",
						new SigninResponse(result.accessToken())
				)
            );
	}

	@PostMapping("/signout")
	public ResponseEntity<CommonResponse<Void>> signout(
			HttpServletRequest httpServletRequest
	) {

		String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorization == null || !authorization.startsWith("Bearer ")) {

			throw new AccessTokenRequiredException();
		}

		signoutUseCase.signout(
				new SignOutCommand(authorization.substring("Bearer ".length()), Role.CORPORATION)
		);

		return CommonResponse.ok("로그아웃 되었습니다.").toResponseEntity();
	}

	@PostMapping("/refresh")
	public ResponseEntity<CommonResponse<RefreshTokenResponse>> refresh(
			@CookieValue(name = "refreshToken", required = false) String refreshToken
	) {

		if (refreshToken == null || refreshToken.isBlank()) {

			throw new RefreshTokenRequiredException();
		}

		String accessToken = refreshTokenUseCase.refresh(
				new RefreshTokenCommand(refreshToken, Role.CORPORATION)
		);

		return CommonResponse.ok(
				"토큰이 재발급 되었습니다.",
				new RefreshTokenResponse(accessToken)
		).toResponseEntity();
	}
}
