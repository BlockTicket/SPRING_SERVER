package tikitaka.service.member.domain.nts_business;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NtsBusiness {

	private String bNo; // 사업자등록번호

	private String startAt; // 개업일자

	private String pNm; // 대표자 성명(개인정보)

	private String bNm; // 상호명
}
