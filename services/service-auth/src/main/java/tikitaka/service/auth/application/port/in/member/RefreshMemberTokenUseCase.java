package tikitaka.service.auth.application.port.in.member;

public interface RefreshMemberTokenUseCase {

	String refresh(RefreshMemberTokenCommand refreshMemberTokenCommand);
}
