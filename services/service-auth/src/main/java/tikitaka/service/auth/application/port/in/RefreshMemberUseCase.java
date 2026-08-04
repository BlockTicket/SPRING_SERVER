package tikitaka.service.auth.application.port.in;

public interface RefreshMemberUseCase {

    LoginMemberResult refresh(
            RefreshMemberCommand refreshMemberCommand
    );
}
