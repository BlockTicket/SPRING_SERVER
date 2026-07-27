package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.AccountType;
import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.TokenPair;
import tikitaka.service.member.domain.auth.TokenType;

import java.util.UUID;

public interface JwtTokenPort {

	TokenPair createTokenPair(
			UUID accountId,
			AccountType accountType
	);

	String createAccessToken(
			UUID accountId,
			AccountType accountType
	);

	AuthTokenClaims parse(
			String token,
			TokenType expectedTokenType
	);
}
