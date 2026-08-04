package tikitaka.service.auth.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.auth.application.port.out.LoadMemberRefreshTokenPort;
import tikitaka.service.auth.application.port.out.SaveMemberRefreshTokenPort;
import tikitaka.service.auth.domain.MemberRefreshToken;
import tikitaka.service.auth.domain.exception.exception.InvalidRefreshTokenException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberRefreshTokenPersistenceAdapter implements
        SaveMemberRefreshTokenPort,
        LoadMemberRefreshTokenPort {

    private final MemberRefreshTokenJpaRepository memberRefreshTokenJpaRepository;

    @Override
    public void saveMemberRefreshToken(
            MemberRefreshToken memberRefreshToken
    ) {

        memberRefreshTokenJpaRepository.save(
                MemberRefreshTokenJpaEntity.builder()
                        .memberId(memberRefreshToken.getMemberId())
                        .refreshToken(memberRefreshToken.getRefreshToken())
                        .expiredAt(memberRefreshToken.getExpiredAt())
                        .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public MemberRefreshToken loadMemberRefreshTokenByMemberId(
            UUID memberId
    ) {

        return memberRefreshTokenJpaRepository.findById(memberId)
                .map(entity -> new MemberRefreshToken(
                        entity.getMemberId(),
                        entity.getRefreshToken(),
                        entity.getExpiredAt()
                ))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberRefreshToken loadMemberRefreshToken(
            String refreshToken
    ) {

        MemberRefreshTokenJpaEntity entity =
                memberRefreshTokenJpaRepository.findByRefreshToken(refreshToken);

        if (entity == null) {
            throw new InvalidRefreshTokenException();
        }

        return new MemberRefreshToken(
                entity.getMemberId(),
                entity.getRefreshToken(),
                entity.getExpiredAt()
        );
    }
}
