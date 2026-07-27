package tikitaka.service.member.application.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;
import tikitaka.service.member.application.port.in.auth.*;
import tikitaka.service.member.application.port.out.auth.*;
import tikitaka.service.member.config.JwtProperties;
import tikitaka.service.member.domain.exception.exception.auth.InvalidAccessTokenException;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RefreshTokenService implements
		MemberTokenRefreshUseCase,
		CorporationTokenRefreshUseCase,
		MemberLogoutUseCase,
		CorporationLogoutUseCase {

	private final SaveMemberRefreshTokenPort saveMemberRefreshTokenPort;
	private final SaveCorporationRefreshTokenPort saveCorporationRefreshTokenPort;
	private final LoadMemberRefreshTokenPort loadMemberRefreshTokenPort;
	private final LoadCorporationRefreshTokenPort loadCorporationRefreshTokenPort;
	private final DeleteMemberRefreshTokenPort deleteMemberRefreshTokenPort;
	private final DeleteCorporationRefreshTokenPort deleteCorporationRefreshTokenPort;
	private final JwtPort jwtPort;
	private final JwtProperties jwtProperties;

	public void saveMemberRefreshToken(
			UUID memberId,
			String refreshToken
	) {

		saveMemberRefreshTokenPort.saveMemberRefreshToken(
				memberId,
				refreshToken,
				getRefreshTokenExpiredAt()
		);
	}

	public void saveCorporationRefreshToken(
			UUID corporationId,
			String refreshToken
	) {

		saveCorporationRefreshTokenPort.saveCorporationRefreshToken(
				corporationId,
				refreshToken,
				getRefreshTokenExpiredAt()
		);
	}

	@Override
	public LoginResponse memberTokenRefresh(
			MemberRefreshTokenCommand memberRefreshTokenCommand
	) {

		UUID memberId = getMemberId(memberRefreshTokenCommand.refreshToken());

		validateMemberRefreshToken(
				memberId,
				memberRefreshTokenCommand.refreshToken()
		);

		String accessToken = jwtPort.generateAccessToken(memberId);
		String refreshToken = jwtPort.generateRefreshToken(memberId);

		saveMemberRefreshToken(memberId, refreshToken);

		return new LoginResponse(accessToken, refreshToken);
	}

	@Override
	public LoginResponse corporationTokenRefresh(
			CorporationRefreshTokenCommand corporationRefreshTokenCommand
	) {

		UUID corporationId = getMemberId(corporationRefreshTokenCommand.refreshToken());

		validateCorporationRefreshToken(
				corporationId,
				corporationRefreshTokenCommand.refreshToken()
		);

		String accessToken = jwtPort.generateAccessToken(corporationId);
		String refreshToken = jwtPort.generateRefreshToken(corporationId);

		saveCorporationRefreshToken(corporationId, refreshToken);

		return new LoginResponse(accessToken, refreshToken);
	}

	@Override
	public void memberLogout(
			MemberLogoutCommand memberLogoutCommand
	) {

		UUID memberId = getAccessTokenMemberId(memberLogoutCommand.accessToken());

		if (loadMemberRefreshTokenPort.loadMemberRefreshTokenByMemberId(memberId).isEmpty()) {

			throw new InvalidAccessTokenException();
		}

		deleteMemberRefreshTokenPort.deleteMemberRefreshTokenByMemberId(memberId);
	}

	@Override
	public void corporationLogout(
			CorporationLogoutCommand corporationLogoutCommand
	) {

		UUID corporationId = getAccessTokenMemberId(corporationLogoutCommand.accessToken());

		if (loadCorporationRefreshTokenPort.loadCorporationRefreshTokenByCorporationId(corporationId).isEmpty()) {

			throw new InvalidAccessTokenException();
		}

		deleteCorporationRefreshTokenPort.deleteCorporationRefreshTokenByCorporationId(corporationId);
	}

	private UUID getMemberId(String refreshToken) {

		if (!jwtPort.validateRefreshToken(refreshToken)) {

			throw new InvalidRefreshTokenException();
		}

		return jwtPort.getMemberId(refreshToken);
	}

	private UUID getAccessTokenMemberId(String accessToken) {

		if (!jwtPort.validateAccessToken(accessToken)) {

			throw new InvalidAccessTokenException();
		}

		return jwtPort.getMemberId(accessToken);
	}

	private void validateMemberRefreshToken(
			UUID memberId,
			String refreshToken
	) {

		String savedRefreshToken = loadMemberRefreshTokenPort
				.loadMemberRefreshTokenByMemberId(memberId)
				.orElseThrow(InvalidRefreshTokenException::new);

		if (!savedRefreshToken.equals(refreshToken)) {

			throw new InvalidRefreshTokenException();
		}
	}

	private void validateCorporationRefreshToken(
			UUID corporationId,
			String refreshToken
	) {

		String savedRefreshToken = loadCorporationRefreshTokenPort
				.loadCorporationRefreshTokenByCorporationId(corporationId)
				.orElseThrow(InvalidRefreshTokenException::new);

		if (!savedRefreshToken.equals(refreshToken)) {

			throw new InvalidRefreshTokenException();
		}
	}

	private LocalDateTime getRefreshTokenExpiredAt() {

		return LocalDateTime.now().plus(
				Duration.ofMillis(jwtProperties.refreshTokenExpiration())
		);
	}
}
