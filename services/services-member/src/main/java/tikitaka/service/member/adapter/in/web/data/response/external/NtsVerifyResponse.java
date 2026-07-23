package tikitaka.service.member.adapter.in.web.data.response.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NtsVerifyResponse(
		String status_code,
		Integer request_cnt,
		Integer valid_cnt,
		List<Data> data
) {

	public record Data(
			String b_no,
			String valid,
			String valid_msg
	) {

		public record RequestParam(
				String b_no,
				String start_at,
				String p_nm,
				String p_nm2,
				String b_nm,
				String corp_no,
				String b_sector,
				String b_type,
				String b_adr
		) {}
	}

	public record Status(
			String b_no,
			String b_stt,
			String b_stt_cd,
			String tax_type,
			String end_at,
			String utcc_yn,
			String tax_type_change_dt,
			String invoice_apply_dt,
			String rbf_tax_type,
			String rbf_tax_type_cd
	) {}
}
