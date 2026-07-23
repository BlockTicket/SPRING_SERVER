package tikitaka.service.performance.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID performanceId;

    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(length = 200)
    private String location;
    @Column(length = 20)
    private String ageLimit;

    @Builder
    public PerformanceJpaEntity(
            String title, String description, String location, String ageLimit
    ) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.ageLimit = ageLimit;
    }
}
