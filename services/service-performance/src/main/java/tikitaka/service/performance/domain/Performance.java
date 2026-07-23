package tikitaka.service.performance.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Performance {

    private final UUID performanceId;
    private final String title;
    private final String description;
    private final String location;
    private final String ageLimit;

    public static Performance create(
            String title, String description, String location, String ageLimit
    ) {
        return new Performance(
                null,
                title,
                description,
                location,
                ageLimit
        );
    }
}