package tikitaka.service.member.adapter.out.persistence.external.nts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaEntity;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaRepository;
import tikitaka.service.member.application.port.out.external.SaveNtsBusinessPort;
import tikitaka.service.member.domain.exception.exception.member.MemberNotFoundException;
import tikitaka.service.member.domain.exception.exception.nts.NtsBusinessNumberAlreadyExistException;
import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SaveNtsBusinessAdapter implements SaveNtsBusinessPort {

	private final MemberJpaRepository memberJpaRepository;
	private final NtsBusinessJpaRepository ntsBusinessJpaRepository;

	@Override
	public void saveNtsBusiness(
			UUID corporationId,
			NtsBusiness ntsBusiness
	) {

		duplicateCheck(ntsBusiness.getBNo());

		MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(corporationId)
				.orElseThrow(MemberNotFoundException::new);

		ntsBusinessJpaRepository.save(NtsBusinessJpaEntity.builder()
				.memberJpaEntity(memberJpaEntity)
				.bNo(ntsBusiness.getBNo())
				.startAt(ntsBusiness.getStartAt())
				.pNm(ntsBusiness.getPNm())
				.bNm(ntsBusiness.getBNm())
				.build()
		);
	}

	private void duplicateCheck(
			String bNo
	) {

		NtsDuplicationCheck ntsDuplicationCheck = ntsBusinessJpaRepository.ntsDuplicationCheck(
				bNo
		);

		if (ntsDuplicationCheck.getBNoExists() > 0) throw new NtsBusinessNumberAlreadyExistException();
	}
}
