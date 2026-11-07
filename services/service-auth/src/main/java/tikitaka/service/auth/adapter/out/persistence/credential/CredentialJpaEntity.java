package tikitaka.service.auth.adapter.out.persistence.credential;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tikitaka.service.auth.domain.role.Role;

import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(
		name = "credential",
		uniqueConstraints = @UniqueConstraint(
				name = "uk_credential_username_role",
				columnNames = { "username", "role" }
		)
)
public class CredentialJpaEntity {

	@Id
	@Column(nullable = false)
	private UUID id;

	@Column(nullable = false, length = 50)
	private String username;

	@Setter
	@Column(nullable = false, length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;
}
