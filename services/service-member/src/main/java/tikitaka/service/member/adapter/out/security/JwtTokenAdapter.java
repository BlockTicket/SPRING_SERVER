package tikitaka.service.member.adapter.out.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tikitaka.service.member.application.port.out.auth.JwtTokenPort;
import tikitaka.service.member.domain.auth.AccountType;
import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.TokenPair;
import tikitaka.service.member.domain.auth.TokenType;
import tikitaka.service.member.domain.exception.exception.auth.ExpiredTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenTypeException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenAdapter implements JwtTokenPort {

	private static final String ACCOUNT_TYPE_CLAIM = "accountType";
	private static final String TOKEN_TYPE_CLAIM = "tokenType";

	private final SecretKey secretKey;
	private final Duration accessTokenExpiration;
	private final Duration refreshTokenExpiration;

	public JwtTokenAdapter(
			@Value("${JWT_SECRET}") String jwtSecret,
			@Value("${JWT_ACCESS_TOKEN_EXPIRATION}") long accessTokenExpirationSeconds,
			@Value("${JWT_REFRESH_TOKEN_EXPIRATION}") long refreshTokenExpirationSeconds
	) {

		this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpiration = Duration.ofSeconds(accessTokenExpirationSeconds);
		this.refreshTokenExpiration = Duration.ofSeconds(refreshTokenExpirationSeconds);
	}

	@Override
	public TokenPair createMemberTokenPair(UUID accountId) {

		return new TokenPair(
				createToken(accountId, AccountType.MEMBER, TokenType.ACCESS, accessTokenExpiration),
				createToken(accountId, AccountType.MEMBER, TokenType.REFRESH, refreshTokenExpiration),
				refreshTokenExpiration.toSeconds()
		);
	}

	@Override
	public TokenPair createCorporationTokenPair(UUID accountId) {

		return new TokenPair(
				createToken(accountId, AccountType.CORPORATION, TokenType.ACCESS, accessTokenExpiration),
				createToken(accountId, AccountType.CORPORATION, TokenType.REFRESH, refreshTokenExpiration),
				refreshTokenExpiration.toSeconds()
		);
	}

	@Override
	public String createMemberAccessToken(UUID accountId) {

		return createToken(accountId, AccountType.MEMBER, TokenType.ACCESS, accessTokenExpiration);
	}

	@Override
	public String createCorporationAccessToken(UUID accountId) {

		return createToken(accountId, AccountType.CORPORATION, TokenType.ACCESS, accessTokenExpiration);
	}

	@Override
	public AuthTokenClaims parseAccessToken(String token) {

		return parse(token, TokenType.ACCESS);
	}

	@Override
	public AuthTokenClaims parseRefreshToken(String token) {

		return parse(token, TokenType.REFRESH);
	}

	@Override
	public void validateMemberClaims(AuthTokenClaims authTokenClaims) {

		if (authTokenClaims.accountType() != AccountType.MEMBER) throw new InvalidTokenTypeException();
	}

	@Override
	public void validateCorporationClaims(AuthTokenClaims authTokenClaims) {

		if (authTokenClaims.accountType() != AccountType.CORPORATION) throw new InvalidTokenTypeException();
	}

	private AuthTokenClaims parse(
			String token,
			TokenType expectedTokenType
	) {

		try {

			Claims claims = Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();

			TokenType tokenType = TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM, String.class));

			if (tokenType != expectedTokenType) throw new InvalidTokenTypeException();

			return new AuthTokenClaims(
					claims.getId(),
					UUID.fromString(claims.getSubject()),
					AccountType.valueOf(claims.get(ACCOUNT_TYPE_CLAIM, String.class)),
					tokenType,
					claims.getExpiration().toInstant()
			);
		} catch (ExpiredJwtException e) {

			throw new ExpiredTokenException();
		} catch (InvalidTokenTypeException e) {

			throw e;
		} catch (JwtException | IllegalArgumentException | NullPointerException e) {

			throw new InvalidTokenException();
		}
	}

	private String createToken(
			UUID accountId,
			AccountType accountType,
			TokenType tokenType,
			Duration expiration
	) {

		Instant now = Instant.now();

		return Jwts.builder()
				.subject(accountId.toString())
				.id(UUID.randomUUID().toString())
				.claim(ACCOUNT_TYPE_CLAIM, accountType.name())
				.claim(TOKEN_TYPE_CLAIM, tokenType.name())
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expiration)))
				.signWith(secretKey)
				.compact();
	}
}
