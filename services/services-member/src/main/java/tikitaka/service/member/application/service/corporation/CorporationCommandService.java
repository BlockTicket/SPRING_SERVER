package tikitaka.service.member.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.member.adapter.in.web.exception.exception.nts.NtsVerificationException;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationCommand;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationUseCase;
import tikitaka.service.member.application.port.in.external.NtsVerificationCommand;
import tikitaka.service.member.application.port.out.corporation.SaveCorporationPort;
import tikitaka.service.member.application.port.out.external.NtsVerificationPort;
import tikitaka.service.member.application.port.out.external.SaveNtsBusinessPort;
import tikitaka.service.member.domain.corporation.Corporation;
import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporationCommandService implements RegisterCorporationUseCase {

	private final SaveCorporationPort saveCorporationPort;
	private final NtsVerificationPort ntsVerificationPort;
	private final SaveNtsBusinessPort saveNtsBusinessPort;

	@Override
	public void registerCorporation(
			RegisterCorporationCommand registerCorporationCommand
	) {

		NtsBusiness business = registerCorporationCommand.ntsBusiness();

		NtsVerificationCommand ntsVerificationCommand = new NtsVerificationCommand(
				business.getBNo(),
				business.getStartAt(),
				business.getPNm(),
				business.getBNm()
		);

		if (!ntsVerificationPort.validate(ntsVerificationCommand).valid()) throw new NtsVerificationException();

		UUID corporationId = registerCorporationCommand.id();

		saveCorporationPort.saveCorporation(new Corporation(
				corporationId,
				registerCorporationCommand.username(),
				registerCorporationCommand.email(),
				registerCorporationCommand.phone(),
				registerCorporationCommand.password(),
				registerCorporationCommand.userType()
		));

		saveNtsBusinessPort.saveNtsBusiness(
				corporationId,
				business
		);
	}
}
