package tikitaka.service.member.adapter.out.persistence.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.corporation.ChangeCorporationPasswordPort;
import tikitaka.service.member.domain.exception.exception.member.InvalidPasswordException;
import tikitaka.service.member.domain.exception.exception.member.MemberNotFoundException;
import tikitaka.service.member.domain.exception.exception.member.SameAsCurrentPasswordException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ChangeCorporationPasswordAdapter implements ChangeCorporationPasswordPort {

	private final PasswordEncoder passwordEncoder;
	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public void changePassword(
			UUID id,
			String currentPassword,
			String newPassword
	) {

		CorporationJpaEntity corporationJpaEntity = corporationJpaRepository.findById(id)
				.orElseThrow(MemberNotFoundException::new);

		if (!passwordEncoder.matches(currentPassword, corporationJpaEntity.getPassword())) throw new InvalidPasswordException();
		if (passwordEncoder.matches(newPassword, corporationJpaEntity.getPassword())) throw new SameAsCurrentPasswordException();

		corporationJpaEntity.setPassword(passwordEncoder.encode(newPassword));
	}
}
