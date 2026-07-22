package tikitaka.service.member.adapter.out.persistence.member;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tikitaka.service.member.domain.enums.Provider;
import tikitaka.service.member.domain.enums.UserType;

import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
public class MemberJpaEntity {

	@Id
	@Column(unique = true, nullable = false)
	private UUID id;

	@Column(unique = true, length = 50, nullable = false)
	private String username;

	@Column(unique = true, length = 100, nullable = false)
	private String email;

	@Column(unique = true, length = 13)
	private String phone;

	@Column(length = 600)
	private String password;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Enumerated(EnumType.STRING)
	private Provider provider;
}
