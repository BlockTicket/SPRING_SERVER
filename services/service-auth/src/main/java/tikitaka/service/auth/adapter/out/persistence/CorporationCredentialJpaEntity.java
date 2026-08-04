package tikitaka.service.auth.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Getter
@Entity
@Immutable
@NoArgsConstructor
@Table(name = "corporation")
public class CorporationCredentialJpaEntity {

    @Id
    private UUID id;

    @Column(length = 50)
    private String username;

    @Column(length = 255)
    private String password;
}
