package tikitaka.service.auth.adapter.out.persistence.refresh_token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tikitaka.service.auth.domain.role.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@IdClass(RefreshTokenId.class)
@Table(name = "refresh_token")
public class RefreshTokenJpaEntity {

	@Id
	@Column(nullable = false)
	private UUID userId;

	@Id
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;

	@Setter
	@Column(nullable = false, length = 512)
	private String token;

	@Setter
	@Column(nullable = false)
	private LocalDateTime expiresAt;
}
