package tikitaka.service.member.domain.corporation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Corporation {

	private UUID id;

	private String username;

	private String email;

	private String phone;

	private String password;
}
