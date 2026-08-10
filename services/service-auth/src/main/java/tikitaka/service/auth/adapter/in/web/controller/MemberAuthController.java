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
import tikitaka.service.auth.adapter.in.web.data.request.SigninMemberRequest;
import tikitaka.service.auth.adapter.in.web.data.response.RefreshTokenResponse;
import tikitaka.service.auth.adapter.in.web.data.response.SigninResponse;
import tikitaka.service.auth.application.port.in.member.RefreshMemberTokenCommand;
import tikitaka.service.auth.application.port.in.member.RefreshMemberTokenUseCase;
import tikitaka.service.auth.application.port.in.member.SigninMemberResult;
import tikitaka.service.auth.application.port.in.member.SigninMemberUseCase;
import tikitaka.service.auth.application.port.in.member.SignoutMemberCommand;
import tikitaka.service.auth.application.port.in.member.SignoutMemberUseCase;
import tikitaka.service.auth.domain.exception.exception.AccessTokenRequiredException;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenRequiredException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/member")
public class MemberAuthController {

	private static final String BEARER_PREFIX = "Bearer ";

	private final SigninMemberUseCase signinMemberUseCase;
	private final SignoutMemberUseCase signoutMemberUseCase;
	private final RefreshMemberTokenUseCase refreshMemberTokenUseCase;

	@PostMapping("/signin")
	public ResponseEntity<CommonResponse<SigninResponse>> signin(
			@Valid @RequestBody SigninMemberRequest signinMemberRequest
	) {

		SigninMemberResult result = signinMemberUseCase.signin(
				signinMemberRequest.toCommand()
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
				));
	}

	@PostMapping("/signout")
	public ResponseEntity<CommonResponse<Void>> signout(
			HttpServletRequest httpServletRequest
	) {

		String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {

			throw new AccessTokenRequiredException();
		}

		signoutMemberUseCase.signout(
				new SignoutMemberCommand(authorization.substring(BEARER_PREFIX.length()))
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

		String accessToken = refreshMemberTokenUseCase.refresh(
				new RefreshMemberTokenCommand(refreshToken)
		);

		return CommonResponse.ok(
				"토큰이 재발급 되었습니다.",
				new RefreshTokenResponse(accessToken)
		).toResponseEntity();
	}
}
