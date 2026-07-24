package tikitaka.service.member.adapter.out.persistence.external.nts.client.data.request;

import tikitaka.service.member.application.port.in.external.NtsVerificationCommand;

import java.util.List;

public record NtsVerifyRequest(
		List<Business> businesses
) {

	public record Business(
			String b_no, // 사업자등록 번호

			String start_dt, // 등록일

			String p_nm, // 대표자 성명

			String b_nm // 상호(명)
	) {
	}
}