package tikitaka.service.member.application.port.in.auth;

public interface MemberLogoutUseCase {

	void memberLogout(MemberRefreshTokenCommand memberRefreshTokenCommand);
}
