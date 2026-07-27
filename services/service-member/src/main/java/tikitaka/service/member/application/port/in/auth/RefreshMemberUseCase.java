package tikitaka.service.member.application.port.in.auth;

public interface RefreshMemberUseCase {

	LoginMemberResult refresh(
			RefreshMemberCommand refreshMemberCommand
	);
}