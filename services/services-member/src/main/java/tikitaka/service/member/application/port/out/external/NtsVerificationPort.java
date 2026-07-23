package tikitaka.service.member.application.port.out.external;

import tikitaka.service.member.application.port.in.external.NtsVerificationCommand;

public interface NtsVerificationPort {

	NtsVerificationResult validate(NtsVerificationCommand ntsVerificationCommand);
}
