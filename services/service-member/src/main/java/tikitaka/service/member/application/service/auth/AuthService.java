package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.in.auth.LoginCommand;
import tikitaka.service.member.application.port.in.auth.LoginUseCase;
import tikitaka.service.member.application.port.in.auth.LogoutCommand;
import tikitaka.service.member.application.port.in.auth.LogoutUseCase;
import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenCommand;
import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenUseCase;
import tikitaka.service.member.application.port.out.auth.AuthenticationAccountPort;
import tikitaka.service.member.application.port.out.auth.InvalidAccessTokenPort;
import tikitaka.service.member.application.port.out.auth.JwtTokenPort;
import tikitaka.service.member.application.port.out.auth.RefreshTokenPort;
import tikitaka.service.member.domain.auth.AuthTokenClaims;
import tikitaka.service.member.domain.auth.AuthenticationAccount;
import tikitaka.service.member.domain.auth.AccountType;
import tikitaka.service.member.domain.auth.InvalidAccessToken;
import tikitaka.service.member.domain.auth.RefreshToken;
import tikitaka.service.member.domain.auth.TokenPair;
import tikitaka.service.member.domain.auth.TokenType;
import tikitaka.service.member.domain.exception.exception.auth.InvalidLoginCredentialsException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidTokenTypeException;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuthService implements LoginUseCase, LogoutUseCase, ReissueAccessTokenUseCase {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationAccountPort authenticationAccountPort;
	private final JwtTokenPort jwtTokenPort;
	private final RefreshTokenPort refreshTokenPort;
	private final InvalidAccessTokenPort invalidAccessTokenPort;

	@Override
	public TokenPair login(LoginCommand loginCommand) {

		AuthenticationAccount authenticationAccount = authenticationAccountPort.findByUsername(
					loginCommand.username(),
					loginCommand.accountType()
				)
				.orElseThrow(InvalidLoginCredentialsException::new);

		if (!passwordEncoder.matches(loginCommand.password(), authenticationAccount.password())) {

			throw new InvalidLoginCredentialsException();
		}

		refreshTokenPort.deleteByAccountIdAndAccountType(
				authenticationAccount.id(),
			authenticationAccount.accountType()
		);

		TokenPair tokenPair = jwtTokenPort.createTokenPair(
				authenticationAccount.id(),
				authenticationAccount.accountType()
		);
		AuthTokenClaims refreshTokenClaims = jwtTokenPort.parse(
				tokenPair.refreshToken(),
				TokenType.REFRESH
		);

		refreshTokenPort.save(new RefreshToken(
				refreshTokenClaims.tokenId(),
				refreshTokenClaims.accountId(),
				refreshTokenClaims.accountType(),
				refreshTokenClaims.expiresAt()
		));

		return tokenPair;
	}

	@Override
	public void logout(LogoutCommand logoutCommand) {

		AuthTokenClaims accessTokenClaims = logoutCommand.accessTokenClaims();

		validateAccountType(accessTokenClaims, logoutCommand.accountType());

		invalidAccessTokenPort.save(new InvalidAccessToken(
				accessTokenClaims.tokenId(),
				accessTokenClaims.expiresAt()
		));

		if (logoutCommand.refreshToken() != null && !logoutCommand.refreshToken().isBlank()) {

			AuthTokenClaims refreshTokenClaims = jwtTokenPort.parse(
					logoutCommand.refreshToken(),
					TokenType.REFRESH
			);

			validateSameAccount(accessTokenClaims, refreshTokenClaims);
		}

		refreshTokenPort.deleteByAccountIdAndAccountType(
				accessTokenClaims.accountId(),
				accessTokenClaims.accountType()
		);
	}

	@Override
	@Transactional(readOnly = true)
	public String reissueAccessToken(ReissueAccessTokenCommand reissueAccessTokenCommand) {

		AuthTokenClaims refreshTokenClaims = jwtTokenPort.parse(
				reissueAccessTokenCommand.refreshToken(),
				TokenType.REFRESH
		);

		validateAccountType(refreshTokenClaims, reissueAccessTokenCommand.accountType());

		RefreshToken refreshToken = refreshTokenPort.findByTokenId(refreshTokenClaims.tokenId())
				.orElseThrow(InvalidTokenException::new);

		if (!refreshToken.accountId().equals(refreshTokenClaims.accountId())
				|| refreshToken.accountType() != refreshTokenClaims.accountType()
				|| !refreshToken.expiresAt().equals(refreshTokenClaims.expiresAt())) {

			throw new InvalidTokenException();
		}

		return jwtTokenPort.createAccessToken(
				refreshTokenClaims.accountId(),
				refreshTokenClaims.accountType()
		);
	}

	private void validateAccountType(
			AuthTokenClaims authTokenClaims,
			AccountType accountType
	) {

		if (authTokenClaims.accountType() != accountType) throw new InvalidTokenTypeException();
	}

	private void validateSameAccount(
			AuthTokenClaims accessTokenClaims,
			AuthTokenClaims refreshTokenClaims
	) {

		if (!accessTokenClaims.accountId().equals(refreshTokenClaims.accountId())
				|| accessTokenClaims.accountType() != refreshTokenClaims.accountType()) {

			throw new InvalidTokenException();
		}
	}
}
