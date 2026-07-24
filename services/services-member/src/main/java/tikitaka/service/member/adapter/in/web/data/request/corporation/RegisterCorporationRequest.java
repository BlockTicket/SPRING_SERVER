package tikitaka.service.member.adapter.in.web.data.request.corporation;

import com.l98293.phone.Format;
import com.l98293.phone.Phone;
import com.l98293.phone.Region;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationCommand;
import tikitaka.service.member.domain.enums.UserType;
import tikitaka.service.member.domain.nts_business.NtsBusiness;

import java.util.UUID;

public record RegisterCorporationRequest(
		@NotBlank
		String username,

		@Email
		@NotBlank
		String email,

		@NotBlank
		@Phone(
				region = Region.KR,
				format = Format.LOCAL
		)
		String phone,

		@NotBlank
		@Pattern(
				regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,32}$",
				message = "비밀번호는 8자 이상 32자 이하이어야 하며, 영문 대소문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다."
		)
		String password,

		@Valid
		@NotNull
		Business business
) {

	public record Business(
			@NotBlank
			String b_no, // 사업자등록 번호

			@NotBlank
			String start_dt, // 등록일

			@NotBlank
			String p_nm, // 대표자 성명

			@NotBlank
			String b_nm // 상호(명)
	) {}

	public RegisterCorporationCommand toCommand() {

		return new RegisterCorporationCommand(
				UUID.randomUUID(),
				username,
				email,
				phone,
				password,
				UserType.CORPORATION,
				new NtsBusiness(
						business.b_no(),
						business.start_dt(),
						business.p_nm(),
						business.b_nm()
				)
		);
	}
}
