package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.auth.DeleteMemberRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.LoadMemberRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.SaveMemberRefreshTokenPort;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberRefreshTokenPersistenceAdapter implements
		SaveMemberRefreshTokenPort,
		LoadMemberRefreshTokenPort,
		DeleteMemberRefreshTokenPort {

	private final MemberRefreshTokenJpaRepository memberRefreshTokenJpaRepository;

	@Override
	public void saveMemberRefreshToken(
			UUID memberId,
			String refreshToken,
			LocalDateTime expiredAt
	) {

		MemberRefreshTokenJpaEntity memberRefreshTokenJpaEntity = memberRefreshTokenJpaRepository
				.findByMemberId(memberId)
				.orElse(null);

		if (memberRefreshTokenJpaEntity != null) {

			memberRefreshTokenJpaEntity.updateRefreshToken(
					refreshToken,
					expiredAt
			);

			return;
		}

		memberRefreshTokenJpaRepository.save(
				MemberRefreshTokenJpaEntity.builder()
						.memberId(memberId)
						.refreshToken(refreshToken)
						.expiredAt(expiredAt)
						.build()
		);
	}

	@Override
	public Optional<String> loadMemberRefreshTokenByMemberId(UUID memberId) {

		return memberRefreshTokenJpaRepository.findByMemberId(memberId)
				.map(MemberRefreshTokenJpaEntity::getRefreshToken);
	}

	@Override
	public void deleteMemberRefreshTokenByMemberId(UUID memberId) {

		memberRefreshTokenJpaRepository.deleteByMemberId(memberId);
	}
}
