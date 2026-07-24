package tikitaka.service.member.adapter.out.persistence.external.nts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.adapter.in.web.exception.exception.nts.NtsBusinessNumberAlreadyExistException;
import tikitaka.service.member.adapter.out.persistence.corporation.CorporationJpaEntity;
import tikitaka.service.member.adapter.out.persistence.corporation.CorporationJpaRepository;
import tikitaka.service.member.application.port.out.external.SaveNtsBusinessPort;
import tikitaka.service.member.adapter.in.web.exception.exception.corporation.CorporationNotFoundException;
import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SaveNtsBusinessAdapter implements SaveNtsBusinessPort {

	private final CorporationJpaRepository corporationJpaRepository;
	private final NtsBusinessJpaRepository ntsBusinessJpaRepository;

	@Override
	public void saveNtsBusiness(
			UUID corporationId,
			NtsBusiness ntsBusiness
	) {

		NtsDuplicationCheck ntsDuplicationCheck = ntsBusinessJpaRepository.ntsDuplicationCheck(
				ntsBusiness.getBNo()
		);

		if (ntsDuplicationCheck.getBNoExists() > 0) throw new NtsBusinessNumberAlreadyExistException();

		CorporationJpaEntity corporationJpaEntity = corporationJpaRepository.findById(corporationId)
				.orElseThrow(CorporationNotFoundException::new);

		ntsBusinessJpaRepository.save(NtsBusinessJpaEntity.builder()
				.corporationJpaEntity(corporationJpaEntity)
				.bNo(ntsBusiness.getBNo())
				.startAt(ntsBusiness.getStartAt())
				.pNm(ntsBusiness.getPNm())
				.bNm(ntsBusiness.getBNm())
				.build()
		);
	}
}
