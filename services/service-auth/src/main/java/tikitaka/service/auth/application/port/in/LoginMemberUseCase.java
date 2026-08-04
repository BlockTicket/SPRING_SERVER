package tikitaka.service.auth.application.port.in;

public interface LoginMemberUseCase {

    LoginMemberResult loginMember(
            LoginMemberCommand loginMemberCommand
    );
}
