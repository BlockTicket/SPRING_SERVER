package tikitaka.service.auth.adapter.out.password;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tikitaka.service.auth.application.port.out.password.PasswordMatcherPort;

import java.util.UUID;

@Component
public class PasswordEncoderAdapter implements PasswordMatcherPort {

	private final PasswordEncoder passwordEncoder;
	private final String dummyHash;

	public PasswordEncoderAdapter(
			PasswordEncoder passwordEncoder
	) {

		this.passwordEncoder = passwordEncoder;
		this.dummyHash = passwordEncoder.encode(UUID.randomUUID().toString());
	}

	@Override
	public boolean matches(
			String rawPassword,
			String encodedPassword
	) {

		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@Override
	public void matchesDummy(
			String rawPassword
	) {

		passwordEncoder.matches(rawPassword, dummyHash);
	}
}
