package tikitaka.service.member.application.port.in.auth.member;

import tikitaka.service.member.application.port.in.auth.ReissueAccessTokenCommand;

public interface MemberReissueAccessTokenUseCase {

	String reissueAccessToken(ReissueAccessTokenCommand reissueAccessTokenCommand);
}
