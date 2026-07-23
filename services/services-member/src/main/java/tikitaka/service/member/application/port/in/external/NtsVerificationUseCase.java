package tikitaka.service.member.application.port.in.external;

import tikitaka.service.member.application.port.out.external.NtsVerificationResult;

public interface NtsVerificationUseCase {

	void validate(NtsVerificationCommand ntsVerificationCommand);
}