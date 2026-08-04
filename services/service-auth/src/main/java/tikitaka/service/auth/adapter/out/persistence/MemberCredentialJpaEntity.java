package tikitaka.service.auth.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

/**
 * service-member가 소유한 member 테이블에 대한 읽기 전용 매핑.
 * 이 서비스는 절대 이 테이블에 쓰기를 하지 않습니다.
 */
@Getter
@Entity
@Immutable
@NoArgsConstructor
@Table(name = "member")
public class MemberCredentialJpaEntity {

    @Id
    private UUID id;

    @Column(length = 50)
    private String username;

    @Column(length = 255)
    private String password;
}
