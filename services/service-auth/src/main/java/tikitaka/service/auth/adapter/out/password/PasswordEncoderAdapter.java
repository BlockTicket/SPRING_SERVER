package tikitaka.service.auth.adapter.out.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tikitaka.service.auth.application.port.out.password.PasswordMatcherPort;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements PasswordMatcherPort {

	private final PasswordEncoder passwordEncoder;

	@Override
	public boolean matches(
			String rawPassword,
			String encodedPassword
	) {

		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
