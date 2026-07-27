package tikitaka.service.member.adapter.in.web.data.response.auth;

import tikitaka.service.member.domain.auth.TokenPair;

public record LoginTokenResponse(
		String accessToken,
		String refreshToken
) {

	public static LoginTokenResponse from(TokenPair tokenPair) {

		return new LoginTokenResponse(
				tokenPair.accessToken(),
				tokenPair.refreshToken()
		);
	}
}
