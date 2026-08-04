package tikitaka.service.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MemberCredential {

    private UUID id;

    private String username;

    private String password;
}
