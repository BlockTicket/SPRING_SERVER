package tikitaka.core.kafka.event;

import java.util.UUID;

public record CorporationRegisteredEvent(
        UUID userId,
        String username,
        String password
) {
}
