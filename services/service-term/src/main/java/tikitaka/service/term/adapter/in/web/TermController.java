package tikitaka.service.term.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.term.adapter.in.web.data.request.CreateTermRequest;
import tikitaka.service.term.adapter.in.web.data.request.UpdateTermRequest;
import tikitaka.service.term.adapter.in.web.data.response.TermResponse;
import tikitaka.service.term.adapter.in.web.data.response.TermUrlResponse;
import tikitaka.service.term.application.port.in.CreateTermUseCase;
import tikitaka.service.term.application.port.in.DeleteTermUseCase;
import tikitaka.service.term.application.port.in.GetTermUseCase;
import tikitaka.service.term.application.port.in.GetTermsUseCase;
import tikitaka.service.term.application.port.in.UpdateTermUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TermController {

	private final CreateTermUseCase createTermUseCase;
	private final GetTermUseCase getTermUseCase;
	private final GetTermsUseCase getTermsUseCase;
	private final UpdateTermUseCase updateTermUseCase;
	private final DeleteTermUseCase deleteTermUseCase;

	@PostMapping("/term")
	public ResponseEntity<CommonResponse<TermUrlResponse>> createTerm(
			@Valid @RequestBody CreateTermRequest createTermRequest
	) {

		UUID termId = createTermUseCase.createTerm(createTermRequest.toCommand());

		return CommonResponse.ok(
				"이용약관이 등록되었습니다.",
				new TermUrlResponse("/api/term/" + termId)
		).toResponseEntity();
	}

	@GetMapping("/terms")
	public ResponseEntity<CommonResponse<List<TermResponse>>> getTerms() {

		List<TermResponse> terms = getTermsUseCase.getTerms().stream()
				.map(TermResponse::from)
				.toList();

		return CommonResponse.ok("전체 이용약관을 조회했습니다.", terms).toResponseEntity();
	}

	@GetMapping("/term/{id}")
	public ResponseEntity<CommonResponse<TermResponse>> getTerm(
			@PathVariable UUID id
	) {

		TermResponse term = TermResponse.from(getTermUseCase.getTerm(id));

		return CommonResponse.ok("이용약관을 조회했습니다.", term).toResponseEntity();
	}

	@PatchMapping("/term")
	public ResponseEntity<CommonResponse<TermUrlResponse>> updateTerm(
			@Valid @RequestBody UpdateTermRequest updateTermRequest
	) {

		updateTermUseCase.updateTerm(updateTermRequest.toCommand());

		return CommonResponse.ok(
				"이용약관이 수정되었습니다.",
				new TermUrlResponse("/api/term/" + updateTermRequest.id())
		).toResponseEntity();
	}

	@DeleteMapping("/term/{id}")
	public ResponseEntity<CommonResponse<Void>> deleteTerm(
			@PathVariable UUID id
	) {

		deleteTermUseCase.deleteTerm(id);

		return CommonResponse.ok("이용약관이 삭제되었습니다.").toResponseEntity();
	}
}
