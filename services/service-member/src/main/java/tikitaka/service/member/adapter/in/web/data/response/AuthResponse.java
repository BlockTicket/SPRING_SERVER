package tikitaka.service.member.adapter.in.web.data.response;

import tikitaka.service.member.application.port.in.auth.LoginCorporationResult;
import tikitaka.service.member.application.port.in.auth.LoginMemberResult;

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