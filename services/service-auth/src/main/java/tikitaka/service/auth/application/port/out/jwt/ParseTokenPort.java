package tikitaka.service.auth.application.port.out.jwt;

import tikitaka.service.auth.domain.token.TokenType;

public interface ParseTokenPort {

	TokenPayload parse(
			String token,
			TokenType expectedType
	);
}
