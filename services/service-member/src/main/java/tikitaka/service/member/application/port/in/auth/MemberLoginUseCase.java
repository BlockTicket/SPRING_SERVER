package tikitaka.service.member.application.port.in.auth;

import tikitaka.service.member.adapter.in.web.data.response.auth.LoginResponse;

public interface MemberLoginUseCase {

	LoginResponse memberLogin(MemberLoginCommand memberLoginCommand);
}
