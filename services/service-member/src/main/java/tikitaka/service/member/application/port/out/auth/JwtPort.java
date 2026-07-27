package tikitaka.service.member.application.port.out.auth;

import java.util.UUID;

public interface JwtPort {

    String generateAccessToken(
            UUID memberId
    );

    String generateRefreshToken(
            UUID memberId
    );

	boolean validateToken(
			String token
	);

	boolean validateRefreshToken(
			String token
	);

	UUID getMemberId(
            String token
    );
}
