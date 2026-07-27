package tikitaka.service.member.adapter.out.persistence.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "invalid_access_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvalidAccessTokenJpaEntity {

	@Id
	@Column(length = 36)
	private String tokenId;

	@Column(nullable = false)
	private Instant expiresAt;

	@Builder
	public InvalidAccessTokenJpaEntity(
			String tokenId,
			Instant expiresAt
	) {

		this.tokenId = tokenId;
		this.expiresAt = expiresAt;
	}
}
