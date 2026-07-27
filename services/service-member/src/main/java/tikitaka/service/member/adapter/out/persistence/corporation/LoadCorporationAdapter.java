package tikitaka.service.member.adapter.out.persistence.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.out.corporation.LoadCorporationPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.auth.AuthException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoadCorporationAdapter implements LoadCorporationPort {

	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public Corporation loadCorporationByUsername(String username) {

		CorporationJpaEntity corporationJpaEntity = corporationJpaRepository.findByUsername(username)
				.orElseThrow(AuthException::new);

		return new Corporation(
				corporationJpaEntity.getId(),
				corporationJpaEntity.getUsername(),
				corporationJpaEntity.getEmail(),
				corporationJpaEntity.getPhone(),
				corporationJpaEntity.getPassword()
		);
	}
}
