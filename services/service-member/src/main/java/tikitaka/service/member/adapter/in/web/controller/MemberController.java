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
import tikitaka.service.member.adapter.in.web.data.request.member.ChangeMemberUsernameRequest;
import tikitaka.service.member.adapter.in.web.data.request.member.RegisterMemberRequest;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationUseCase;
import tikitaka.service.member.application.port.in.member.ChangeMemberUsernameUseCase;
import tikitaka.service.member.application.port.in.member.RegisterMemberUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;
	private final RegisterCorporationUseCase registerCorporationUseCase;
	private final ChangeMemberUsernameUseCase changeMemberUsernameUseCase;

	// 회원가입
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

	// 사용자명 변경
	@PostMapping("/member/username")
	public ResponseEntity<CommonResponse<Void>> changeMemberUsername(
			@Valid @RequestBody ChangeMemberUsernameRequest changeMemberUsernameRequest
	) {

		changeMemberUsernameUseCase.changeUsername(
				changeMemberUsernameRequest.toCommand()
		);

		return CommonResponse.ok("사용자명이 변경되었습니다.").toResponseEntity();
	}
}
