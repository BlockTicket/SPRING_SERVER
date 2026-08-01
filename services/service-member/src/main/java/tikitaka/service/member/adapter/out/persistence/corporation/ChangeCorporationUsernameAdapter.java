package tikitaka.service.member.adapter.out.persistence.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.corporation.ChangeCorporationUsernamePort;
import tikitaka.service.member.domain.exception.exception.member.MemberNotFoundException;
import tikitaka.service.member.domain.exception.exception.member.SameAsCurrentUsernameException;
import tikitaka.service.member.domain.exception.exception.member.UsernameAlreadyExistException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ChangeCorporationUsernameAdapter implements ChangeCorporationUsernamePort {

	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public void changeUsername(
			UUID id,
			String newUsername
	) {

		CorporationJpaEntity corporationJpaEntity = corporationJpaRepository.findById(id)
				.orElseThrow(MemberNotFoundException::new);

		if (corporationJpaEntity.getUsername().equals(newUsername)) throw new SameAsCurrentUsernameException();

		if (corporationJpaRepository.existsByUsernameAndIdNot(newUsername, id)) {

			throw new UsernameAlreadyExistException();
		}

		corporationJpaEntity.setUsername(newUsername);
	}
}
