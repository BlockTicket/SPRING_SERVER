package tikitaka.service.member.adapter.out.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.member.application.port.out.auth.JwtPort;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider implements JwtPort {

	private final JwtProperties jwtProperties;


	private SecretKey getKey() {

		return Keys.hmacShaKeyFor(
				jwtProperties.getSecret()
						.getBytes(StandardCharsets.UTF_8)
		);
	}


	@Override
	public String createAccessToken(
			UUID id,
			String type
	) {

		return createToken(
				id,
				type,
				jwtProperties.getAccessExpiration()
		);
	}


	@Override
	public String createRefreshToken(
			UUID id,
			String type
	) {

		return createToken(
				id,
				type,
				jwtProperties.getRefreshExpiration()
		);
	}


	private String createToken(
			UUID id,
			String type,
			long expiration
	) {

		Date now = new Date();

		return Jwts.builder()
				.subject(id.toString())
				.claim("type", type)
				.issuedAt(now)
				.expiration(
						new Date(
								now.getTime() + expiration
						)
				)
				.signWith(getKey())
				.compact();
	}


	@Override
	public boolean validateToken(
			String token
	) {

		try {

			Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(token);

			return true;

		} catch (Exception e) {

			return false;
		}
	}


	@Override
	public UUID getId(
			String token
	) {

		return UUID.fromString(
				getClaims(token)
						.getSubject()
		);
	}


	@Override
	public String getType(
			String token
	) {

		return getClaims(token)
				.get("type", String.class);
	}


	private Claims getClaims(
			String token
	) {

		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}