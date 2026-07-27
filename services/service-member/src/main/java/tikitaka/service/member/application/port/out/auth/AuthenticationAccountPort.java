package tikitaka.service.member.application.port.out.auth;

import tikitaka.service.member.domain.auth.AuthenticationAccount;

import java.util.Optional;

public interface AuthenticationAccountPort {

	Optional<AuthenticationAccount> findMemberByUsername(String username);

	Optional<AuthenticationAccount> findCorporationByUsername(String username);
}
