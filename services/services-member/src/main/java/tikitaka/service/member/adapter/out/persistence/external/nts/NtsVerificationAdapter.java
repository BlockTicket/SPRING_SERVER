package tikitaka.service.member.adapter.out.persistence.external.nts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.member.adapter.out.persistence.external.nts.client.data.request.NtsVerifyRequest;
import tikitaka.service.member.adapter.out.persistence.external.nts.client.data.response.NtsVerifyResponse;
import tikitaka.service.member.adapter.out.persistence.external.nts.client.NtsApiClient;
import tikitaka.service.member.application.port.out.external.NtsVerificationRequest;
import tikitaka.service.member.application.port.out.external.NtsVerificationResult;
import tikitaka.service.member.application.port.out.external.NtsVerificationPort;
import tikitaka.service.member.domain.exception.exception.nts.NtsBodyException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NtsVerificationAdapter implements NtsVerificationPort {

	private final NtsApiClient ntsApiClient;

	@Override
	public NtsVerificationResult validate(
			NtsVerificationRequest ntsVerificationRequest
	) {

		NtsVerifyResponse ntsVerifyResponse = ntsApiClient.validate(toRequest(ntsVerificationRequest));

		if (ntsVerifyResponse.data() == null || ntsVerifyResponse.data().isEmpty()) throw new NtsBodyException();

		return new NtsVerificationResult("01".equals(ntsVerifyResponse.data().getFirst().valid()));
	}

	private NtsVerifyRequest toRequest(
			NtsVerificationRequest ntsVerificationRequest
	) {

		return new NtsVerifyRequest(
				List.of(
						new NtsVerifyRequest.Business(
								ntsVerificationRequest.bNo(),
								ntsVerificationRequest.startDt(),
								ntsVerificationRequest.pNm(),
								ntsVerificationRequest.bNm()
						)
				)
		);
	}
}
