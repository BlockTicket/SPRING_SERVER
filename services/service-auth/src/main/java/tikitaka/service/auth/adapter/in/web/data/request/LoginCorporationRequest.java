package tikitaka.service.auth.adapter.in.web.data.request;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.auth.application.port.in.LoginCorporationCommand;

public record LoginCorporationRequest(

        @NotBlank
        String username,

        @NotBlank
        String password,

        boolean rememberMe

) {

    public LoginCorporationCommand toCommand() {

        return new LoginCorporationCommand(
                username,
                password,
                rememberMe
        );
    }
}
