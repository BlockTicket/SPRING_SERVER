package tikitaka.service.auth.domain.refresh_token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tikitaka.service.auth.domain.role.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RefreshToken {

	private UUID userId;

	private Role role;

	private String token;

	private LocalDateTime expiresAt;
}
