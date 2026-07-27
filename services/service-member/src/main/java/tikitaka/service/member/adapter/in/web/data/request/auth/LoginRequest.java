package tikitaka.service.member.adapter.in.web.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import tikitaka.service.member.application.port.in.auth.CorporationLoginCommand;
import tikitaka.service.member.application.port.in.auth.MemberLoginCommand;

public record LoginRequest(

        @NotBlank
        String username,

        @NotBlank
        String password

) {

    public MemberLoginCommand toMemberLoginCommand() {

        return new MemberLoginCommand(
                username,
                password
        );
    }

    public CorporationLoginCommand toCorporationLoginCommand() {

        return new CorporationLoginCommand(
                username,
                password
        );
    }
}
