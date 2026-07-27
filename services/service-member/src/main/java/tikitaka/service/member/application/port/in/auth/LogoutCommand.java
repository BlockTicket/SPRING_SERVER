package tikitaka.service.member.application.port.in.auth;

import tikitaka.service.member.domain.auth.AuthTokenClaims;

public record LogoutCommand(
		AuthTokenClaims accessTokenClaims,
		String refreshToken
) {
}
