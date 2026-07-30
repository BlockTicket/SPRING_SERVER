package tikitaka.service.member.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.member.adapter.in.web.data.request.corporation.ChangeCorporationUsernameRequest;
import tikitaka.service.member.adapter.in.web.data.request.corporation.RegisterCorporationRequest;
import tikitaka.service.member.adapter.in.web.data.request.member.ChangeMemberUsernameRequest;
import tikitaka.service.member.adapter.in.web.data.request.member.RegisterMemberRequest;
import tikitaka.service.member.application.port.in.corporation.ChangeCorporationUsernameUseCase;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationUseCase;
import tikitaka.service.member.application.port.in.member.ChangeMemberUsernameUseCase;
import tikitaka.service.member.application.port.in.member.RegisterMemberUseCase;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;
	private final RegisterCorporationUseCase registerCorporationUseCase;
	private final ChangeMemberUsernameUseCase changeMemberUsernameUseCase;
	private final ChangeCorporationUsernameUseCase changeCorporationUsernameUseCase;

	// 상태확인
	@GetMapping("/health")
	public ResponseEntity<CommonResponse<Map<String, Boolean>>> health() {

		return CommonResponse.health(Map.of("health", true)).toResponseEntity();
	}

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
	@PatchMapping("/member/username")
	public ResponseEntity<CommonResponse<Void>> changeMemberUsername(
			@Valid @RequestBody ChangeMemberUsernameRequest changeMemberUsernameRequest
	) {

		changeMemberUsernameUseCase.changeUsername(
				changeMemberUsernameRequest.toCommand()
		);

		return CommonResponse.ok("사용자명이 변경되었습니다.").toResponseEntity();
	}

	@PatchMapping("/corporation/username")
	public ResponseEntity<CommonResponse<Void>> changeCorporationUsername(
			@Valid @RequestBody ChangeCorporationUsernameRequest changeCorporationUsernameRequest
	) {

		changeCorporationUsernameUseCase.changeCorporationUsername(
				changeCorporationUsernameRequest.toCommand()
		);

		return CommonResponse.ok("사용자명이 변경되었습니다.").toResponseEntity();
	}
}
