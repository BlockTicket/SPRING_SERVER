package tikitaka.service.auth.application.port.out;

import java.util.UUID;

public interface JwtPort {

    String createAccessToken(
            UUID id,
            String type
    );

    String createRefreshToken(
            UUID id,
            String type
    );

    boolean validateToken(
            String token
    );

    UUID getId(
            String token
    );

    String getType(
            String token
    );
}
