package tikitaka.service.auth.adapter.out.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import tikitaka.service.auth.application.port.out.jwt.IssueAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.IssueRefreshTokenPort;
import tikitaka.service.auth.application.port.out.jwt.ParseTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.domain.exception.exception.ExpiredTokenException;
import tikitaka.service.auth.domain.exception.exception.InvalidTokenException;
import tikitaka.service.auth.domain.role.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtAdapter implements ParseTokenPort, IssueAccessTokenPort, IssueRefreshTokenPort {

	private static final String CLAIM_ROLE = "role";

	private final SecretKey secretKey;
	private final Duration accessTokenTtl;
	private final Duration refreshTokenTtl;

	public JwtAdapter(
			JwtProperties jwtProperties
	) {

		this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
		this.accessTokenTtl = Duration.ofSeconds(jwtProperties.accessTokenValiditySeconds());
		this.refreshTokenTtl = Duration.ofSeconds(jwtProperties.refreshTokenValiditySeconds());
	}

	@Override
	public TokenPayload parse(
			String token
	) {

		try {

			Claims claims = Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();

			return new TokenPayload(
					UUID.fromString(claims.getSubject()),
					Role.valueOf(claims.get(CLAIM_ROLE, String.class))
			);
		} catch (ExpiredJwtException e) {

			throw new ExpiredTokenException();
		} catch (JwtException | IllegalArgumentException e) {

			throw new InvalidTokenException();
		}
	}

	@Override
	public IssuedAccessToken issueAccessToken(
			UUID userId,
			Role role
	) {

		return new IssuedAccessToken(
				buildToken(userId, role, accessTokenTtl),
				accessTokenTtl
		);
	}

	@Override
	public IssuedRefreshToken issueRefreshToken(
			UUID userId,
			Role role
	) {

		return new IssuedRefreshToken(
				buildToken(userId, role, refreshTokenTtl),
				refreshTokenTtl
		);
	}

	private String buildToken(
			UUID userId,
			Role role,
			Duration ttl
	) {

		Date now = new Date();
		Date expiration = new Date(now.getTime() + ttl.toMillis());

		return Jwts.builder()
				.subject(userId.toString())
				.claim(CLAIM_ROLE, role.name())
				.issuedAt(now)
				.expiration(expiration)
				.signWith(secretKey)
				.compact();
	}
}
