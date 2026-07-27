package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.member.LoadMemberPort;
import tikitaka.service.member.domain.exception.exception.auth.AuthException;
import tikitaka.service.member.domain.member.Member;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoadMemberAdapter implements LoadMemberPort {

	private final MemberJpaRepository memberJpaRepository;

	@Override
	public Member loadMemberByUsername(String username) {

		MemberJpaEntity memberJpaEntity = memberJpaRepository.findByUsername(username)
				.orElseThrow(AuthException::new);

		return new Member(
				memberJpaEntity.getId(),
				memberJpaEntity.getUsername(),
				memberJpaEntity.getEmail(),
				memberJpaEntity.getPhone(),
				memberJpaEntity.getPassword(),
				memberJpaEntity.getProvider()
		);
	}
}
