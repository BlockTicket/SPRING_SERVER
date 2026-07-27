package tikitaka.service.member.adapter.in.web.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.member.application.port.in.auth.CorporationRefreshTokenCommand;
import tikitaka.service.member.application.port.in.auth.MemberRefreshTokenCommand;

public record RefreshTokenRequest(

		@NotBlank
		String refreshToken

) {

	public MemberRefreshTokenCommand toMemberRefreshTokenCommand() {

		return new MemberRefreshTokenCommand(refreshToken);
	}

	public CorporationRefreshTokenCommand toCorporationRefreshTokenCommand() {

		return new CorporationRefreshTokenCommand(refreshToken);
	}
}
