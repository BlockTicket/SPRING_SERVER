package tikitaka.service.member.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.corporation.RegisterCorporationRequest;
import tikitaka.service.member.adapter.in.web.data.request.member.RegisterMemberRequest;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationUseCase;
import tikitaka.service.member.application.port.in.member.RegisterMemberUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;
	private final RegisterCorporationUseCase registerCorporationUseCase;

	@PostMapping("/member/register")
	public ResponseEntity<CommonResponse<Void>> register(
			@Valid @RequestBody RegisterMemberRequest registerMemberRequest
	) {

		registerMemberUseCase.registerMember(
				registerMemberRequest.toCommand()
		);

		return CommonResponse.ok("회원가입 되었습니다.").toResponseEntity();
	}

	@PostMapping("/corporation/register")
	public ResponseEntity<CommonResponse<Void>> register(
			@Valid @RequestBody RegisterCorporationRequest registerCorporationRequest
	) {

		registerCorporationUseCase.registerCorporation(
				registerCorporationRequest.toCommand()
		);

		return CommonResponse.ok("회원가입 되었습니다.").toResponseEntity();
	}
}
