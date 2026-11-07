package tikitaka.service.auth.application.service.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tikitaka.service.auth.adapter.out.jwt.JwtAdapter;
import tikitaka.service.auth.adapter.out.jwt.JwtProperties;
import tikitaka.service.auth.application.port.in.auth.RefreshTokenCommand;
import tikitaka.service.auth.domain.exception.exception.RefreshTokenMismatchException;
import tikitaka.service.auth.domain.refresh_token.RefreshToken;
import tikitaka.service.auth.domain.role.Role;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RefreshTokenServiceJtiTest {

	private static final String SECRET = "0123456789abcdef0123456789abcdef";

	private JwtAdapter jwtAdapter;
	private AtomicReference<RefreshToken> storedRefreshToken;
	private RefreshTokenService refreshTokenService;

	@BeforeEach
	void setUp() {

		jwtAdapter = new JwtAdapter(new JwtProperties(SECRET, 1800, 1209600));
		storedRefreshToken = new AtomicReference<>();
		refreshTokenService = new RefreshTokenService(
				jwtAdapter,
				(userId, role) -> Optional.ofNullable(storedRefreshToken.get()),
				jwtAdapter,
				(userId, role, accessToken, ttl) -> {
				}
		);
	}

	@Test
	void previousRefreshTokenIsRejectedAfterReissueInSameSecond() {

		UUID userId = UUID.randomUUID();

		String oldToken = signIn(userId);
		String newToken = signIn(userId);

		assertThrows(
				RefreshTokenMismatchException.class,
				() -> refreshTokenService.refresh(new RefreshTokenCommand(oldToken, Role.MEMBER))
		);
		assertNotNull(refreshTokenService.refresh(new RefreshTokenCommand(newToken, Role.MEMBER)));
	}

	private String signIn(
			UUID userId
	) {

		String token = jwtAdapter.issueRefreshToken(userId, Role.MEMBER).token();

		storedRefreshToken.set(new RefreshToken(userId, Role.MEMBER, token, LocalDateTime.now().plusDays(14)));

		return token;
	}
}
