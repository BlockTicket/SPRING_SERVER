package tikitaka.service.auth.adapter.out.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import tikitaka.service.auth.application.port.out.access_token.DeleteAccessTokenPort;
import tikitaka.service.auth.application.port.out.access_token.SaveAccessTokenPort;
import tikitaka.service.auth.domain.role.Role;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccessTokenRedisAdapter implements SaveAccessTokenPort, DeleteAccessTokenPort {

	private final StringRedisTemplate stringRedisTemplate;

	@Override
	public void save(
			UUID userId,
			Role role,
			String accessToken,
			Duration ttl
	) {

		stringRedisTemplate
				.opsForValue()
				.set(buildKey(userId, role), accessToken, ttl);
	}

	@Override
	public void deleteByUserIdAndRole(
			UUID userId,
			Role role
	) {

		stringRedisTemplate.delete(buildKey(userId, role));
	}

	private String buildKey(
			UUID userId,
			Role role
	) {

		return "access_token:" + role.name() + ":" + userId;
	}
}
