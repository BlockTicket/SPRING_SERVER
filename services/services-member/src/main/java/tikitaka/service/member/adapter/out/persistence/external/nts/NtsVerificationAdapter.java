package tikitaka.service.member.adapter.out.persistence.external.nts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.member.adapter.in.web.data.request.external.NtsVerifyRequest;
import tikitaka.service.member.adapter.in.web.data.response.external.NtsVerifyResponse;
import tikitaka.service.member.application.port.in.external.NtsVerificationCommand;
import tikitaka.service.member.application.port.out.external.NtsVerificationResult;
import tikitaka.service.member.application.port.out.external.NtsVerificationPort;
import tikitaka.service.member.adapter.in.web.exception.exception.nts.NtsBodyException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NtsVerificationAdapter implements NtsVerificationPort {

	private final NtsApiClient ntsApiClient;

	@Override
	public NtsVerificationResult validate(
			NtsVerificationCommand ntsVerificationCommand
	) {

		NtsVerifyResponse ntsVerifyResponse = ntsApiClient.validate(toRequest(ntsVerificationCommand));

		if (ntsVerifyResponse.data() == null || ntsVerifyResponse.data().isEmpty()) {

			throw new NtsBodyException();
		}

		return new NtsVerificationResult("01".equals(ntsVerifyResponse.data().getFirst().valid()));
	}

	private NtsVerifyRequest toRequest(
			NtsVerificationCommand ntsVerificationCommand
	) {

		return new NtsVerifyRequest(
				List.of(
						new NtsVerifyRequest.Business(
								ntsVerificationCommand.bNo(),
								ntsVerificationCommand.startDt(),
								ntsVerificationCommand.pNm(),
								ntsVerificationCommand.bNm()
						)
				)
		);
	}
}
