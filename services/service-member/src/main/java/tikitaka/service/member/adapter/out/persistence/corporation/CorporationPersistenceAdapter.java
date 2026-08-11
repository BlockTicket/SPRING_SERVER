package tikitaka.service.member.adapter.out.persistence.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.adapter.out.persistence.member.DuplicateCheck;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaEntity;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaRepository;
import tikitaka.service.member.application.port.out.corporation.SaveCorporationPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.member.EmailAlreadyExistException;
import tikitaka.service.member.domain.exception.exception.member.PhoneAlreadyExistException;
import tikitaka.service.member.domain.exception.exception.member.UsernameAlreadyExistException;
import tikitaka.service.member.domain.member.MemberType;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationPersistenceAdapter implements SaveCorporationPort {

	private final PasswordEncoder passwordEncoder;
	private final MemberJpaRepository memberJpaRepository;
	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public String saveCorporation(
			Corporation corporation
	) {

		duplicateCheck(
				corporation.getUsername(),
				corporation.getEmail(),
				corporation.getPhone()
		);

		String encodedPassword = passwordEncoder.encode(corporation.getPassword());

		MemberJpaEntity memberJpaEntity = memberJpaRepository.save(MemberJpaEntity.builder()
				.id(corporation.getId())
				.username(corporation.getUsername())
				.email(corporation.getEmail())
				.phone(corporation.getPhone())
				.password(encodedPassword)
				.type(MemberType.CORPORATION)
				.provider(null)
				.build()
		);

		corporationJpaRepository.save(CorporationJpaEntity.builder()
				.memberJpaEntity(memberJpaEntity)
				.build()
		);

		return encodedPassword;
	}

	private void duplicateCheck(
			String username,
			String email,
			String phone
	) {

		DuplicateCheck duplicateCheck = memberJpaRepository.checkDuplicate(
				username,
				email,
				phone,
				MemberType.CORPORATION.name()
		);

		if (duplicateCheck.getUsernameExists() > 0) throw new UsernameAlreadyExistException();
		else if (duplicateCheck.getEmailExists() > 0) throw new EmailAlreadyExistException();
		else if (duplicateCheck.getPhoneExists() > 0) throw new PhoneAlreadyExistException();
	}
}
