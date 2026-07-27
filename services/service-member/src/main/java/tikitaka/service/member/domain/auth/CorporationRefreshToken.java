package tikitaka.service.member.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CorporationRefreshToken {

    private UUID corporationId;

    private String refreshToken;

    private LocalDateTime expiredAt;
}