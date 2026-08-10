package tikitaka.service.member.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationCommand;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationUseCase;
import tikitaka.service.member.application.port.out.corporation.SaveCorporationPort;
import tikitaka.service.member.application.port.out.event.PublishCorporationRegisteredEventPort;
import tikitaka.service.member.application.port.out.external.NtsVerificationPort;
import tikitaka.service.member.application.port.out.external.NtsVerificationRequest;
import tikitaka.service.member.application.port.out.external.SaveNtsBusinessPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.exception.exception.nts.NtsVerificationException;
import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationRegisterService implements RegisterCorporationUseCase {

	private final SaveCorporationPort saveCorporationPort;
	private final SaveNtsBusinessPort saveNtsBusinessPort;
	private final NtsVerificationPort ntsVerificationPort;
	private final PublishCorporationRegisteredEventPort publishCorporationRegisteredEventPort;

	@Override
	public void registerCorporation(
			RegisterCorporationCommand registerCorporationCommand
	) {

		NtsBusiness business = registerCorporationCommand.ntsBusiness();

		NtsVerificationRequest ntsVerificationRequest= new NtsVerificationRequest(
				business.getBNo(),
				business.getStartAt(),
				business.getPNm(),
				business.getBNm()
		);

		if (!ntsVerificationPort.validate(ntsVerificationRequest).valid()) throw new NtsVerificationException();

		UUID corporationId = registerCorporationCommand.id();

		String passwordHash = saveCorporationPort.saveCorporation(new Corporation(
				corporationId,
				registerCorporationCommand.username(),
				registerCorporationCommand.email(),
				registerCorporationCommand.phone(),
				registerCorporationCommand.password()
		));

		saveNtsBusinessPort.saveNtsBusiness(
				corporationId,
				business
		);

		publishCorporationRegisteredEventPort.publishCorporationRegistered(
				corporationId,
				registerCorporationCommand.username(),
				passwordHash
		);
	}
}
