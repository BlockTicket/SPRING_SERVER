package tikitaka.service.member.adapter.out.persistence.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "member_refresh_token")
public class MemberRefreshTokenJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private UUID memberId;

	@Column(nullable = false)
	private String refreshToken;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	public void updateRefreshToken(
			String refreshToken,
			LocalDateTime expiredAt
	) {

		this.refreshToken = refreshToken;
		this.expiredAt = expiredAt;
	}
}
