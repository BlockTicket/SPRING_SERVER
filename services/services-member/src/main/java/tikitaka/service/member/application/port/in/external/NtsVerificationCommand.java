package tikitaka.service.member.application.port.in.external;


public record NtsVerificationCommand(
		String bNo,
		String startDt,
		String pNm,
		String bNm
) {
}