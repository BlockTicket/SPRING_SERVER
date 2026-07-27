package tikitaka.service.member.application.port.in.auth.member;

import tikitaka.service.member.application.port.in.auth.LogoutCommand;

public interface MemberLogoutUseCase {

	void logout(LogoutCommand logoutCommand);
}
