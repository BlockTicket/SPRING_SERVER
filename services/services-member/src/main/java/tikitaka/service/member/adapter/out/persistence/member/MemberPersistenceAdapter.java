package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.SaveMemberPort;
import tikitaka.service.member.domain.exception.exception.EmailAlreadyExist;
import tikitaka.service.member.domain.exception.exception.PhoneAlreadyExist;
import tikitaka.service.member.domain.exception.exception.UsernameAlreadyExist;
import tikitaka.service.member.domain.member.Member;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberPersistenceAdapter implements SaveMemberPort {

	private final MemberJpaRepository memberJpaRepository;
	private final PasswordEncoder passwordEncoder;

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
				.password(passwordEncoder.encode(member.getPassword())) // 비밀번호 평문으로 들어감
				.userType(member.getUserType())
				.provider(member.getProvider())
				.build()
		);
	}
}
