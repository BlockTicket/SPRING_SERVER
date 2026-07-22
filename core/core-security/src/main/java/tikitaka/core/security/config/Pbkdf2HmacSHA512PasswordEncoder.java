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

public class Pbkdf2HmacSHA512PasswordEncoder implements PasswordEncoder {

	private static final int SALT_LENGTH = 32;
	private final String pepper;
	private final int iterations;

	public Pbkdf2HmacSHA512PasswordEncoder(
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

			byte[] saltWithBlockIndex = new byte[salt.length + 4];

			System.arraycopy(salt, 0, saltWithBlockIndex, 0, salt.length);

			saltWithBlockIndex[salt.length] = 0;
			saltWithBlockIndex[salt.length + 1] = 0;
			saltWithBlockIndex[salt.length + 2] = 0;
			saltWithBlockIndex[salt.length + 3] = 1;

			byte[] u = mac.doFinal(saltWithBlockIndex);
			byte[] result = u.clone();

			for (int i = 0; i < iterations; i++) {

				u = mac.doFinal(u);

				for (int j = 0; j < result.length; j++) {

					result[j] ^= u[j];
				}
			}

			return result;
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
