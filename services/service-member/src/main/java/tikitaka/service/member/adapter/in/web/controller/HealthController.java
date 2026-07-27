package tikitaka.service.member.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tikitaka.core.common.data.CommonResponse;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

	@GetMapping
	public ResponseEntity<CommonResponse<Map<String, Boolean>>> health() {

		return CommonResponse.health(Map.of("health", true)).toResponseEntity();
	}
}