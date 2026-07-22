package tikitaka.service.member.application.service.corporation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationCommand;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationUseCase;
import tikitaka.service.member.application.port.out.corporation.SaveCorporationPort;
import tikitaka.service.member.domain.corporation.Corporation;

@Service
@RequiredArgsConstructor
public class CorporationCommandService implements RegisterCorporationUseCase {

	private final SaveCorporationPort saveCorporationPort;

	@Override
	public void registerCorporation(
			RegisterCorporationCommand registerCorporationCommand
	) {

		saveCorporationPort.saveCorporation(new Corporation(
				registerCorporationCommand.id(),
				registerCorporationCommand.username(),
				registerCorporationCommand.email(),
				registerCorporationCommand.phone(),
				registerCorporationCommand.password(),
				registerCorporationCommand.userType()
		));
	}
}
