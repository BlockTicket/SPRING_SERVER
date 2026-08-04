package tikitaka.service.auth.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.auth.application.port.in.LoginMemberCommand;

public record LoginMemberRequest(
        @NotBlank
        String username,

        @NotBlank
        String password,

        boolean rememberMe
) {

    public LoginMemberCommand toCommand() {

        return new LoginMemberCommand(
                username,
                password,
                rememberMe
        );
    }
}
