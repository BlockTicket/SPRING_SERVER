package tikitaka.service.member.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Member {

	private UUID id;

	private String username;

	private String email;

	private String phone;

	private String password;

	private Provider provider;
}
