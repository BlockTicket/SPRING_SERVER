package tikitaka.service.auth.application.port.out.password;

public interface PasswordMatcherPort {

	boolean matches(
			String rawPassword,
			String encodedPassword
	);
}
