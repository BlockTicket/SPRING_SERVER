package tikitaka.service.member.application.port.in.auth;

import java.util.UUID;

public record MemberLogoutCommand(
		UUID memberId
) {
}
