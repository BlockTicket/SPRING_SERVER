package tikitaka.service.member.adapter.out.persistence.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tikitaka.service.member.domain.auth.AccountType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenJpaEntity {

	@Id
	@Column(length = 36)
	private String tokenId;

	@Column(nullable = false)
	private UUID accountId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private AccountType accountType;

	@Column(nullable = false)
	private Instant expiresAt;

	@Builder
	public RefreshTokenJpaEntity(
			String tokenId,
			UUID accountId,
			AccountType accountType,
			Instant expiresAt
	) {

		this.tokenId = tokenId;
		this.accountId = accountId;
		this.accountType = accountType;
		this.expiresAt = expiresAt;
	}
}
