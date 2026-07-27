package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.member.ChangeMemberUsernamePort;
import tikitaka.service.member.domain.exception.exception.member.MemberNotFoundException;
import tikitaka.service.member.domain.exception.exception.member.UsernameAlreadyExistException;

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

		if (memberJpaRepository.existsByUsernameAndIdNot(newUsername, id)) {

			throw new UsernameAlreadyExistException();
		}

		memberJpaRepository.findById(id).orElseThrow(MemberNotFoundException::new)
				.setUsername(newUsername);
	}
}
