package tikitaka.service.auth.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "corporation_refresh_token")
public class CorporationRefreshTokenJpaEntity {

    @Id
    private UUID corporationId;

    @Column(nullable = false, length = 1000)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiredAt;
}
