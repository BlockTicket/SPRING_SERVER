package tikitaka.service.member.adapter.in.web.data.request.external;

import tikitaka.service.member.application.port.in.external.NtsVerificationCommand;

import java.util.List;

public record NtsVerifyRequest(
		List<Business> businesses
) {

	public record Business(
			String b_no, // 사업자등록 번호

			String start_dt, // 등록일

			String p_nm, // 대표자 성명

			String b_nm, // 상호(명)

			String b_adr // 사업장 주소
	) {
	}

	public NtsVerificationCommand toCommand() {

		Business business = businesses.getFirst();

		return new NtsVerificationCommand(
				business.b_no(),
				business.start_dt(),
				business.p_nm(),
				business.b_nm(),
				business.b_adr()
		);
	}
}
