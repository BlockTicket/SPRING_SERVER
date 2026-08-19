package tikitaka.service.auth.domain.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tikitaka.service.auth.domain.role.Role;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Credential {

	private UUID id;

	private String username;

	private String password;

	private Role role;
}
