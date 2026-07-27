package tikitaka.service.member.application.port.in.auth;

import java.util.UUID;

public record CorporationLogoutCommand(
		UUID corporationId
) {
}
