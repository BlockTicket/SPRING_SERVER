package tikitaka.service.member.adapter.out.persistence.external.nts;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tikitaka.service.member.adapter.out.persistence.corporation.CorporationJpaEntity;

import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "business_license")
public class NtsBusinessJpaEntity {

	@Id
	@Column(name = "corporation_id")
	private UUID corporationId;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId
	@JoinColumn(name = "corporation_id")
	private CorporationJpaEntity corporationJpaEntity;

	@Column(length = 10, unique = true, nullable = false)
	private String bNo; // 사업자등록번호 ex) 1224567890

	@Column(length = 8, nullable = false)
	private String startAt; // 개업일자, ex) 20000101

	@Column(length = 30, nullable = false)
	private String pNm; // 대표자 성명

	@Column(length = 50, nullable = false)
	private String bNm; // 상호명
}
