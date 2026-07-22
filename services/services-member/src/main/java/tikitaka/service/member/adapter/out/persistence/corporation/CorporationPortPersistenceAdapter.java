package tikitaka.service.member.adapter.out.persistence.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.adapter.out.persistence.member.DuplicateCheck;
import tikitaka.service.member.application.port.out.corporation.SaveCorporationPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.EmailAlreadyExist;
import tikitaka.service.member.domain.exception.exception.PhoneAlreadyExist;
import tikitaka.service.member.domain.exception.exception.UsernameAlreadyExist;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationPortPersistenceAdapter implements SaveCorporationPort {

	private final PasswordEncoder passwordEncoder;
	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public void saveCorporation(Corporation corporation) {

		DuplicateCheck duplicateCheck = corporationJpaRepository.checkDuplicate(
				corporation.getUsername(),
				corporation.getEmail(),
				corporation.getPhone()
		);

		if (duplicateCheck.getUsernameExists() > 0) throw new UsernameAlreadyExist();
		if (duplicateCheck.getEmailExists() > 0) throw new EmailAlreadyExist();
		if (duplicateCheck.getPhoneExists() > 0) throw new PhoneAlreadyExist();

		corporationJpaRepository.save(CorporationJpaEntity.builder()
				.id(corporation.getId())
				.username(corporation.getUsername())
				.email(corporation.getEmail())
				.phone(corporation.getPhone())
				.password(passwordEncoder.encode(corporation.getPassword()))
				.userType(corporation.getUserType())
				.build()
		);
	}
}
