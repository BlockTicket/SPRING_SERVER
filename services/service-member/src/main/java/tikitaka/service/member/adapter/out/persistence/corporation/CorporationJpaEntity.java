package tikitaka.service.member.adapter.out.persistence.corporation;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
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

	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@Column(length = 100, unique = true, nullable = false)
	private String email;

	@Column(length = 13, unique = true, nullable = false)
	private String phone;

	@Column(length = 600, nullable = false)
	private String password;

	@OneToOne(mappedBy = "corporationJpaEntity")
	private NtsBusinessJpaEntity ntsBusinessJpaEntity;
}
