package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.member.SaveMemberPort;
import tikitaka.service.member.domain.exception.exception.EmailAlreadyExist;
import tikitaka.service.member.domain.exception.exception.PhoneAlreadyExist;
import tikitaka.service.member.domain.exception.exception.UsernameAlreadyExist;
import tikitaka.service.member.domain.member.Member;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberPersistenceAdapter implements SaveMemberPort {

	private final PasswordEncoder passwordEncoder;
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public void saveMember(Member member) {

		DuplicateCheck duplicateCheck = memberJpaRepository.checkDuplicate(
				member.getUsername(),
				member.getEmail(),
				member.getPhone()
		);

		if (duplicateCheck.getUsernameExists() > 0) throw new UsernameAlreadyExist();
		else if (duplicateCheck.getEmailExists() > 0) throw new EmailAlreadyExist();
		else if (duplicateCheck.getPhoneExists() > 0) throw new PhoneAlreadyExist();

		memberJpaRepository.save(MemberJpaEntity.builder()
				.id(member.getId())
				.username(member.getUsername())
				.email(member.getEmail())
				.phone(member.getPhone())
				.password(passwordEncoder.encode(member.getPassword()))
				.userType(member.getUserType())
				.provider(member.getProvider())
				.build()
		);
	}
}