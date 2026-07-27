package tikitaka.service.member.application.port.in.auth;

public interface ReissueAccessTokenUseCase {

	String reissueAccessToken(ReissueAccessTokenCommand reissueAccessTokenCommand);
}
