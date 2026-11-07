package tikitaka.service.auth.adapter.out.persistence.refresh_token;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tikitaka.service.auth.domain.role.Role;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RefreshTokenId implements Serializable {

	private UUID userId;

	private Role role;
}
