package tikitaka.service.member.adapter.in.web.data.response.auth;

public record LoginResponse(
		String accessToken,
		String refreshToken
) {
}
