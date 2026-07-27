package tikitaka.service.member.application.port.in.auth.corporation;

import tikitaka.service.member.application.port.in.auth.LoginCommand;
import tikitaka.service.member.domain.auth.TokenPair;

public interface CorporationLoginUseCase {

	TokenPair login(LoginCommand loginCommand);
}
