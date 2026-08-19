package tikitaka.service.member.adapter.out.persistence.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tikitaka.service.member.domain.member.MemberType;
import tikitaka.service.member.domain.member.Provider;

import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(
		name = "member",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_member_username_type", columnNames = { "username", "type" }),
				@UniqueConstraint(name = "uk_member_email_type",    columnNames = { "email", "type" }),
				@UniqueConstraint(name = "uk_member_phone_type",    columnNames = { "phone", "type" })
		}
)
public class MemberJpaEntity {

	@Id
	@Column(nullable = false)
	private UUID id;

	@Setter
	@Getter
	@Column(length = 50, nullable = false)
	private String username;

	@Column(length = 100, nullable = false)
	private String email;

	@Column(length = 13)
	private String phone;

	@Setter
	@Column(length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private MemberType type;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Provider provider;
}
