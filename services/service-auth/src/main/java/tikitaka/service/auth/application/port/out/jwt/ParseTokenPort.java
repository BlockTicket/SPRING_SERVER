package tikitaka.service.auth.application.port.out.jwt;

public interface ParseTokenPort {

	TokenPayload parse(String token);
}
