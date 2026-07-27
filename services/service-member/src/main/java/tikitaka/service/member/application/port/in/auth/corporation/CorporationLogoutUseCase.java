package tikitaka.service.member.application.port.in.auth.corporation;

import tikitaka.service.member.application.port.in.auth.LogoutCommand;

public interface CorporationLogoutUseCase {

	void logout(LogoutCommand logoutCommand);
}
