package tikitaka.service.member.application.port.in.auth;

public interface LoginMemberUseCase {

	LoginMemberResult loginMember(
			LoginMemberCommand loginMemberCommand
	);
}