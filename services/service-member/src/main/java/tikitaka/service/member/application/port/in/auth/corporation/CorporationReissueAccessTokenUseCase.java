package tikitaka.service.member.application.port.in.auth.corporation;

import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenCommand;

public interface CorporationReissueAccessTokenUseCase {

	String reissueAccessToken(ReissueAccessTokenCommand reissueAccessTokenCommand);
}
