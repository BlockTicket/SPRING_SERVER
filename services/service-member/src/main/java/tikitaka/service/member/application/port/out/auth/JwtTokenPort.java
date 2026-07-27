package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.TokenPair;

import java.util.UUID;

public interface JwtTokenPort {

	TokenPair createMemberTokenPair(UUID accountId);

	TokenPair createCorporationTokenPair(UUID accountId);

	String createMemberAccessToken(UUID accountId);

	String createCorporationAccessToken(UUID accountId);

	AuthTokenClaims parseAccessToken(String token);

	AuthTokenClaims parseRefreshToken(String token);

	void validateMemberClaims(AuthTokenClaims authTokenClaims);

	void validateCorporationClaims(AuthTokenClaims authTokenClaims);
}
