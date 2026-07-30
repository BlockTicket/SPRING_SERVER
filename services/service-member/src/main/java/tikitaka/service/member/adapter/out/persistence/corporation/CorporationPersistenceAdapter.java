package tikitaka.service.member.adapter.out.persistence.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.corporation.SaveCorporationPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.member.EmailAlreadyExistException;
import tikitaka.service.member.domain.exception.exception.member.PhoneAlreadyExistException;
import tikitaka.service.member.domain.exception.exception.member.UsernameAlreadyExistException;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationPersistenceAdapter implements SaveCorporationPort {

	private final PasswordEncoder passwordEncoder;
	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public void saveCorporation(
			Corporation corporation
	) {

		duplicateCheck(
				corporation.getUsername(),
				corporation.getEmail(),
				corporation.getPhone()
		);

		corporationJpaRepository.save(CorporationJpaEntity.builder()
				.id(corporation.getId())
				.username(corporation.getUsername())
				.email(corporation.getEmail())
				.phone(corporation.getPhone())
				.password(passwordEncoder.encode(corporation.getPassword()))
				.build()
		);
	}

	private void duplicateCheck(
			String username,
			String email,
			String phone
	) {

		CorporationDuplicateCheck corporationDuplicateCheck = corporationJpaRepository.corporationDuplicateCheck(
				username,
				email,
				phone
		);

		if (corporationDuplicateCheck.getUsernameExists() > 0) throw new UsernameAlreadyExistException();
		if (corporationDuplicateCheck.getEmailExists() > 0) throw new EmailAlreadyExistException();
		if (corporationDuplicateCheck.getPhoneExists() > 0) throw new PhoneAlreadyExistException();
	}
}