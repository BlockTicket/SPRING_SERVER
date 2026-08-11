package tikitaka.service.member.adapter.out.persistence.corporation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tikitaka.service.member.adapter.out.persistence.external.nts.NtsBusinessJpaEntity;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaEntity;

import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "corporation")
public class CorporationJpaEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id")
	private MemberJpaEntity memberJpaEntity;

	@OneToOne(mappedBy = "corporationJpaEntity", fetch = FetchType.LAZY)
	private NtsBusinessJpaEntity ntsBusinessJpaEntity;
}
