package tikitaka.service.member.adapter.in.web.data.request.corporation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.l98293.phone.Format;
import com.l98293.phone.Phone;
import com.l98293.phone.Region;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import tikitaka.service.member.application.port.in.corporation.RegisterCorporationCommand;
import tikitaka.service.member.domain.enums.UserType;

import java.util.UUID;

public record RegisterCorporationRequest(
		@NotBlank
		String username,

		@Email
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
		String password
) {

	public RegisterCorporationCommand toCommand() {

		return new RegisterCorporationCommand(
				UUID.randomUUID(),
				username,
				email,
				phone,
				password,
				UserType.CORPORATION
		);
	}
}
