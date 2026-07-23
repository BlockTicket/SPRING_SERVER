package tikitaka.service.member.adapter.in.web.controller.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.external.NtsVerifyRequest;
import tikitaka.service.member.application.port.in.external.NtsVerificationUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nts/business")
public class NtsController {

	private final NtsVerificationUseCase ntsVerificationUseCase;

	@PostMapping("/validate")
	public ResponseEntity<CommonResponse<Void>> validate(
			@Valid @RequestBody NtsVerifyRequest ntsVerifyRequest
	) {

		ntsVerificationUseCase.validate(
				ntsVerifyRequest.toCommand()
		);

		return CommonResponse.ok("사업자가 인증되었습니다.").toResponseEntity();
	}
}
