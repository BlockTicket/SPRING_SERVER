package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.InvalidAccessToken;

public interface InvalidAccessTokenPort {

	void save(InvalidAccessToken invalidAccessToken);

	boolean isInvalidated(String tokenId);
}
