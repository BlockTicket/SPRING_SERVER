package tikitaka.service.member.adapter.out.persistence.corporation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tikitaka.service.member.adapter.out.persistence.external.nts.NtsBusinessJpaEntity;
import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "corporation")
public class CorporationJpaEntity {

	@Id
	private UUID id;

	@Getter
	@Setter
	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@Column(length = 100, unique = true, nullable = false)
	private String email;

	@Column(length = 13, unique = true, nullable = false)
	private String phone;

	@Setter
	@Column(length = 255, nullable = false)
	private String password;

	@OneToOne(mappedBy = "corporationJpaEntity")
	private NtsBusinessJpaEntity ntsBusinessJpaEntity;
}
