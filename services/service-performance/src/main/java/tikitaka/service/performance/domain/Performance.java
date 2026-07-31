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
    private final String location;
    private final String ageLimit;
    private final String description;

    public Performance(
            String title,
            String location,
            String ageLimit,
            String description
    ) {
        this.performanceId = null;
        this.title = title;
        this.location = location;
        this.ageLimit = ageLimit;
        this.description = description;
    }
}
