package tikitaka.service.member.application.port.out.auth;

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