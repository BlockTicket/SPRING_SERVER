package tikitaka.service.member.domain.nts_business;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NtsBusiness {

	private String bNo; // 사업자등록번호

	private String startAt; // 개업일자

	private String pNm; // 대표자 성명(개인정보)

	private String bNm; // 상호명

	private String bAdr; // 사업장 주소
}
