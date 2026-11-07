package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.member.ChangeMemberUsernamePort;
import tikitaka.service.member.domain.exception.exception.member.MemberNotFoundException;
import tikitaka.service.member.domain.exception.exception.member.SameAsCurrentUsername;
import tikitaka.service.member.domain.exception.exception.member.UsernameAlreadyExistException;
import tikitaka.service.member.domain.member.MemberType;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ChangeMemberUsernameAdapter implements ChangeMemberUsernamePort {

	private final MemberJpaRepository memberJpaRepository;

	@Override
	public void changeUsername(
			UUID id,
			String newUsername
	) {

		MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(id)
				.orElseThrow(MemberNotFoundException::new);

		if (memberJpaEntity.getUsername().equals(newUsername)) throw new SameAsCurrentUsername();

		if (memberJpaRepository.existsByUsernameAndType(newUsername, MemberType.MEMBER)) {

			throw new UsernameAlreadyExistException();
		}

		memberJpaEntity.setUsername(newUsername);
	}
}
