package tikitaka.service.member.application.port.out.external;

public interface NtsVerificationPort {

	NtsVerificationResult validate(NtsVerificationRequest ntsVerificationRequest);
}
