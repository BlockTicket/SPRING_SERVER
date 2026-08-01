package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.member.ChangeMemberPasswordPort;
import tikitaka.service.member.domain.exception.exception.member.InvalidPasswordException;
import tikitaka.service.member.domain.exception.exception.member.MemberNotFoundException;
import tikitaka.service.member.domain.exception.exception.member.SameAsCurrentPasswordException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ChangeMemberPasswordAdapter implements ChangeMemberPasswordPort {

	private final PasswordEncoder passwordEncoder;
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public void changePassword(
			UUID id,
			String currentPassword,
			String newPassword
	) {

		MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(id)
				.orElseThrow(MemberNotFoundException::new);

		if (!passwordEncoder.matches(currentPassword, memberJpaEntity.getPassword())) throw new InvalidPasswordException();
		if (passwordEncoder.matches(newPassword, memberJpaEntity.getPassword())) throw new SameAsCurrentPasswordException();

		memberJpaEntity.setPassword(passwordEncoder.encode(newPassword));
	}
}
