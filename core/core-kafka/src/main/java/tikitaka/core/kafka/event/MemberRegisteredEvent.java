package tikitaka.core.kafka.event;

import java.util.UUID;

public record MemberRegisteredEvent(
        UUID userId,
        String username,
        String password
) {
}
