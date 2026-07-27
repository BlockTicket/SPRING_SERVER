package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.auth.LoadMemberRefreshTokenPort;
import tikitaka.service.member.application.port.out.auth.SaveMemberRefreshTokenPort;
import tikitaka.service.member.domain.auth.MemberRefreshToken;
import tikitaka.service.member.domain.exception.exception.auth.InvalidRefreshTokenException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
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

        MemberRefreshTokenJpaEntity entity =
                memberRefreshTokenJpaRepository.findById(memberId)
                        .orElseThrow(
                                InvalidRefreshTokenException::new
                        );


        return new MemberRefreshToken(
                entity.getMemberId(),
                entity.getRefreshToken(),
                entity.getExpiredAt()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public MemberRefreshToken loadMemberRefreshToken(
            String refreshToken
    ) {

        MemberRefreshTokenJpaEntity entity =
                memberRefreshTokenJpaRepository.findByRefreshToken(
                        refreshToken
                );


        if(entity == null) {

            throw new InvalidRefreshTokenException();
        }


        return new MemberRefreshToken(
                entity.getMemberId(),
                entity.getRefreshToken(),
                entity.getExpiredAt()
        );
    }
}