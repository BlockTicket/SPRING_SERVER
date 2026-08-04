package tikitaka.service.auth.adapter.in.web.data.response;

import tikitaka.service.auth.application.port.in.LoginCorporationResult;
import tikitaka.service.auth.application.port.in.LoginMemberResult;

public record AuthResponse(

        String accessToken

) {

    public static AuthResponse from(
            LoginMemberResult result
    ) {

        return new AuthResponse(
                result.accessToken()
        );
    }

    public static AuthResponse from(
            LoginCorporationResult result
    ) {

        return new AuthResponse(
                result.accessToken()
        );
    }
}
