package tikitaka.service.member.application.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tikitaka.service.member.application.port.in.external.NtsVerificationCommand;
import tikitaka.service.member.application.port.in.external.NtsVerificationUseCase;
import tikitaka.service.member.application.port.out.external.NtsVerificationPort;
import tikitaka.service.member.adapter.in.web.exception.exception.nts.NtsVerificationException;

@Service
@RequiredArgsConstructor
public class NtsVerificationService implements NtsVerificationUseCase {

	private final NtsVerificationPort ntsVerificationPort;

	@Override
	public void validate(
			NtsVerificationCommand ntsVerificationCommand
	) {

		if (!ntsVerificationPort.validate(ntsVerificationCommand).valid()) throw new NtsVerificationException();
	}
}
