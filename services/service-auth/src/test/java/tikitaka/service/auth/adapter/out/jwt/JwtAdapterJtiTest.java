package tikitaka.service.auth.adapter.out.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tikitaka.service.auth.application.port.out.jwt.TokenPayload;
import tikitaka.service.auth.domain.exception.exception.InvalidTokenException;
import tikitaka.service.auth.domain.role.Role;
import tikitaka.service.auth.domain.token.TokenType;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtAdapterJtiTest {

	private static final String SECRET = "0123456789abcdef0123456789abcdef";

	private JwtAdapter jwtAdapter;
	private SecretKey secretKey;

	@BeforeEach
	void setUp() {

		jwtAdapter = new JwtAdapter(new JwtProperties(SECRET, 1800, 1209600));
		secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void issuedTokenContainsJtiAndParseReturnsIt() {

		String token = jwtAdapter.issueAccessToken(UUID.randomUUID(), Role.MEMBER).token();

		String jti = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getId();

		TokenPayload payload = jwtAdapter.parse(token, TokenType.ACCESS_TOKEN);

		assertEquals(UUID.fromString(jti), payload.tokenId());
	}

	@Test
	void tokensIssuedAtSameMomentAreUnique() {

		UUID userId = UUID.randomUUID();
		Set<String> tokens = new HashSet<>();
		Set<UUID> jtis = new HashSet<>();

		for (int i = 0; i < 100; i++) {

			String token = jwtAdapter.issueRefreshToken(userId, Role.MEMBER).token();

			tokens.add(token);
			jtis.add(jwtAdapter.parse(token, TokenType.REFRESH_TOKEN).tokenId());
		}

		assertEquals(100, tokens.size());
		assertEquals(100, jtis.size());
	}

	@Test
	void accessAndRefreshTokenHaveDifferentJti() {

		UUID userId = UUID.randomUUID();
		String access = jwtAdapter.issueAccessToken(userId, Role.MEMBER).token();
		String refresh = jwtAdapter.issueRefreshToken(userId, Role.MEMBER).token();

		assertNotEquals(
				jwtAdapter.parse(access, TokenType.ACCESS_TOKEN).tokenId(),
				jwtAdapter.parse(refresh, TokenType.REFRESH_TOKEN).tokenId()
		);
	}

	@Test
	void tokenWithoutJtiIsRejected() {

		String token = Jwts.builder()
				.subject(UUID.randomUUID().toString())
				.claim("role", Role.MEMBER.name())
				.claim("type", TokenType.ACCESS_TOKEN.claim())
				.expiration(new Date(System.currentTimeMillis() + 60_000))
				.signWith(secretKey)
				.compact();

		assertThrows(InvalidTokenException.class, () -> jwtAdapter.parse(token, TokenType.ACCESS_TOKEN));
	}

	@Test
	void tokenWithMalformedJtiIsRejected() {

		String token = Jwts.builder()
				.id("not-a-uuid")
				.subject(UUID.randomUUID().toString())
				.claim("role", Role.MEMBER.name())
				.claim("type", TokenType.ACCESS_TOKEN.claim())
				.expiration(new Date(System.currentTimeMillis() + 60_000))
				.signWith(secretKey)
				.compact();

		assertThrows(InvalidTokenException.class, () -> jwtAdapter.parse(token, TokenType.ACCESS_TOKEN));
	}
}
