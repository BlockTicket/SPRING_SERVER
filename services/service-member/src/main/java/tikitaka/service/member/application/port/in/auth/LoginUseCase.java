package tikitaka.service.member.application.port.in.auth;

import tikitaka.service.member.domain.auth.TokenPair;

public interface LoginUseCase {

	TokenPair login(LoginCommand loginCommand);
}
