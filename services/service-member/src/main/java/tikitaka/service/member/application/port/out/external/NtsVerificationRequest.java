package tikitaka.service.member.application.port.out.external;

public record NtsVerificationRequest(
		String bNo,
		String startDt,
		String pNm,
		String bNm
) {
}
