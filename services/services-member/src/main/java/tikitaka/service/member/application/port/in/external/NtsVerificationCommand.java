package tikitaka.service.member.application.port.in.external;

import tikitaka.service.member.adapter.in.web.data.request.external.NtsVerifyRequest;

import java.util.List;

public record NtsVerificationCommand(
		String bNo,
		String startDt,
		String pNm,
		String bNm
) {

	public NtsVerifyRequest toRequest(
			NtsVerificationCommand ntsVerificationCommand
	) {

		return new NtsVerifyRequest(
				List.of(
						new NtsVerifyRequest.Business(
								ntsVerificationCommand.bNo,
								ntsVerificationCommand.startDt,
								ntsVerificationCommand.pNm,
								ntsVerificationCommand.bNm
						)
				)
		);
	}
}