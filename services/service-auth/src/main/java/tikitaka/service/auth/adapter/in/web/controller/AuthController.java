package tikitaka.service.auth.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.auth.adapter.in.web.data.request.LoginCorporationRequest;
import tikitaka.service.auth.adapter.in.web.data.request.LoginMemberRequest;
import tikitaka.service.auth.adapter.in.web.data.response.AuthResponse;
import tikitaka.service.auth.application.port.in.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private final LoginMemberUseCase loginMemberUseCase;
    private final LoginCorporationUseCase loginCorporationUseCase;

    private final RefreshMemberUseCase refreshMemberUseCase;
    private final RefreshCorporationUseCase refreshCorporationUseCase;


    @PostMapping("/member/login")
    public ResponseEntity<CommonResponse<AuthResponse>> loginMember(
            @Valid @RequestBody LoginMemberRequest request
    ) {

        LoginMemberResult result =
                loginMemberUseCase.loginMember(
                        request.toCommand()
                );

        ResponseCookie cookie = createRefreshTokenCookie(
                result.refreshToken()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(
                        CommonResponse.ok(
                                "로그인 되었습니다.",
                                AuthResponse.from(result)
                        )
                );
    }


    @PostMapping("/corporation/login")
    public ResponseEntity<CommonResponse<AuthResponse>> loginCorporation(
            @Valid @RequestBody LoginCorporationRequest request
    ) {

        LoginCorporationResult result =
                loginCorporationUseCase.loginCorporation(
                        request.toCommand()
                );

        ResponseCookie cookie = createRefreshTokenCookie(
                result.refreshToken()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(
                        CommonResponse.ok(
                                "로그인 되었습니다.",
                                AuthResponse.from(result)
                        )
                );
    }


    @PostMapping("/member/refresh")
    public ResponseEntity<CommonResponse<AuthResponse>> refreshMember(
            @CookieValue(REFRESH_TOKEN_COOKIE_NAME) String refreshToken
    ) {

        return CommonResponse.ok(
                "토큰 재발급 되었습니다.",
                AuthResponse.from(
                        refreshMemberUseCase.refresh(
                                new RefreshMemberCommand(refreshToken)
                        )
                )
        ).toResponseEntity();
    }


    @PostMapping("/corporation/refresh")
    public ResponseEntity<CommonResponse<AuthResponse>> refreshCorporation(
            @CookieValue(REFRESH_TOKEN_COOKIE_NAME) String refreshToken
    ) {

        return CommonResponse.ok(
                "토큰 재발급 되었습니다.",
                AuthResponse.from(
                        refreshCorporationUseCase.refresh(
                                new RefreshCorporationCommand(refreshToken)
                        )
                )
        ).toResponseEntity();
    }


    private ResponseCookie createRefreshTokenCookie(
            String refreshToken
    ) {

        return ResponseCookie
                .from(
                        REFRESH_TOKEN_COOKIE_NAME,
                        refreshToken
                )
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();
    }
}
