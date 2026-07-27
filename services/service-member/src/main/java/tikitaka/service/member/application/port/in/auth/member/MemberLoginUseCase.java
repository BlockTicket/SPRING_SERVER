package tikitaka.service.member.application.port.in.auth.member;

import tikitaka.service.member.application.port.in.auth.LoginCommand;
import tikitaka.service.member.domain.auth.TokenPair;

public interface MemberLoginUseCase {

	TokenPair login(LoginCommand loginCommand);
}
