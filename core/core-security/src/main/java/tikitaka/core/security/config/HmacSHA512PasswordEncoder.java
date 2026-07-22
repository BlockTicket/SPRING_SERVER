package tikitaka.core.security.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public class HmacSHA512PasswordEncoder implements PasswordEncoder {

	private static final int SALT_LENGTH = 32;
	private final String pepper;
	private final int iterations;

	public HmacSHA512PasswordEncoder(
			String pepper,
			int iterations
	) {

		if (iterations < 100_000) {

			throw new IllegalArgumentException("iterations length is too short");
		}

		this.pepper = pepper;
		this.iterations = iterations;
	}

	@Override
	public @Nullable String encode(@Nullable CharSequence rawPassword) {

		Objects.requireNonNull(rawPassword);

		byte[] salt = generateSalt();
		byte[] hash = hashWithSalt(rawPassword.toString(), salt, this.iterations);

		return Base64.getEncoder().encodeToString(salt)
				+ "$"
				+ this.iterations
				+ "$"
				+ Base64.getEncoder().encodeToString(hash);
	}

	@Override
	public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {

		if (rawPassword == null || encodedPassword == null) { return false; }

		try {

			String[] parts = encodedPassword.split("\\$", 3);

			if (parts.length != 3) { return false; }

			byte[] salt = Base64.getDecoder().decode(parts[0]);
			int iterations = Integer.parseInt(parts[1]);
			byte[] savedHash = Base64.getDecoder().decode(parts[2]);

			byte[] targetHash = hashWithSalt(rawPassword.toString(), salt, iterations);

			return MessageDigest.isEqual(targetHash, savedHash);
		} catch (IllegalArgumentException e) {

			return false;
		}
	}

	private byte[] hashWithSalt(
			String password,
			byte[] salt,
			int iterations
	) {

		try {

			Mac mac = Mac.getInstance("HmacSHA512");
			SecretKeySpec key = new SecretKeySpec(
					(password + this.pepper).getBytes(StandardCharsets.UTF_8),
					"HmacSHA512"
			);

			mac.init(key);
			mac.update(salt);

			byte[] hash = mac.doFinal();

			for (int i = 0; i < iterations; i++) {

				mac.reset();
				mac.update(hash);

				hash = mac.doFinal();
			}

			return hash;
		} catch (Exception e) {

			throw new RuntimeException("Cannot create pw hash");
		}
	}

	private byte[] generateSalt() {

		byte[] salt = new byte[SALT_LENGTH];

		new SecureRandom().nextBytes(salt);

		return salt;
	}
}
