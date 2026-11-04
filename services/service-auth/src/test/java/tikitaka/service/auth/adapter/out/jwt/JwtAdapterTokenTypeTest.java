package tikitaka.service.auth.adapter.out.jwt;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tikitaka.service.auth.application.port.out.jwt.IssueAccessTokenPort;
import tikitaka.service.auth.application.port.out.jwt.IssueRefreshTokenPort;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.domain.exception.exception.InvalidTokenException;
import tikitaka.service.auth.domain.role.Role;
import tikitaka.service.auth.domain.token.TokenType;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtAdapterTokenTypeTest {

	private static final String SECRET = "0123456789abcdef0123456789abcdef";

	private JwtAdapter jwtAdapter;
	private SecretKey secretKey;

	@BeforeEach
	void setUp() {

		jwtAdapter = new JwtAdapter(new JwtProperties(SECRET, 1800, 1209600));
		secretKey = io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void refreshTokenIsRejectedAsAccessToken() {

		UUID userId = UUID.randomUUID();
		IssueRefreshTokenPort.IssuedRefreshToken refresh = jwtAdapter.issueRefreshToken(userId, Role.MEMBER);

		assertEquals(TokenType.REFRESH_TOKEN.claim(), typeOf(refresh.token()));
		assertThrows(InvalidTokenException.class, () -> jwtAdapter.parse(refresh.token(), TokenType.ACCESS_TOKEN));

		TokenPayload payload = jwtAdapter.parse(refresh.token(), TokenType.REFRESH_TOKEN);

		assertEquals(userId, payload.userId());
		assertEquals(Role.MEMBER, payload.role());
	}

	@Test
	void accessTokenIsRejectedAsRefreshToken() {

		UUID userId = UUID.randomUUID();
		IssueAccessTokenPort.IssuedAccessToken access = jwtAdapter.issueAccessToken(userId, Role.CORPORATION);

		assertEquals(TokenType.ACCESS_TOKEN.claim(), typeOf(access.token()));
		assertThrows(InvalidTokenException.class, () -> jwtAdapter.parse(access.token(), TokenType.REFRESH_TOKEN));

		TokenPayload payload = jwtAdapter.parse(access.token(), TokenType.ACCESS_TOKEN);

		assertEquals(userId, payload.userId());
		assertEquals(Role.CORPORATION, payload.role());
	}

	private String typeOf(
			String token
	) {

		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("type", String.class);
	}
}
