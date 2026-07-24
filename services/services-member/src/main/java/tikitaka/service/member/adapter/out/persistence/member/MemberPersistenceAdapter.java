package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.member.SaveMemberPort;
import tikitaka.service.member.domain.exception.exception.member.EmailAlreadyExistException;
import tikitaka.service.member.domain.exception.exception.member.PhoneAlreadyExistException;
import tikitaka.service.member.domain.exception.exception.member.UsernameAlreadyExistException;
import tikitaka.service.member.domain.member.Member;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberPersistenceAdapter implements SaveMemberPort {

	private final PasswordEncoder passwordEncoder;
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public void saveMember(Member member) {

		duplicateCheck(
				member.getUsername(),
				member.getEmail(),
				member.getPhone()
		);

		memberJpaRepository.save(MemberJpaEntity.builder()
				.id(member.getId())
				.username(member.getUsername())
				.email(member.getEmail())
				.phone(member.getPhone())
				.password(passwordEncoder.encode(member.getPassword()))
				.provider(member.getProvider())
				.build()
		);
	}

	private void duplicateCheck(
			String username,
			String email,
			String phone
	) {

		DuplicateCheck duplicateCheck = memberJpaRepository.checkDuplicate(
				username,
				email,
				phone
		);

		if (duplicateCheck.getUsernameExists() > 0) throw new UsernameAlreadyExistException();
		else if (duplicateCheck.getEmailExists() > 0) throw new EmailAlreadyExistException();
		else if (duplicateCheck.getPhoneExists() > 0) throw new PhoneAlreadyExistException();
	}
}