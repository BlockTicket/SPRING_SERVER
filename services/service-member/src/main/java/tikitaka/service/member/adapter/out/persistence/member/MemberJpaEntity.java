package tikitaka.service.member.adapter.out.persistence.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tikitaka.service.member.domain.member.Provider;

import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "member")
public class MemberJpaEntity {

	@Id
	@Column(unique = true, nullable = false)
	private UUID id;

	@Setter
	@Getter
	@Column(unique = true, length = 50, nullable = false)
	private String username;

	@Column(unique = true, length = 100, nullable = false)
	private String email;

	@Column(unique = true, length = 13)
	private String phone;

	@Setter
	@Column(length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	private Provider provider;
}
