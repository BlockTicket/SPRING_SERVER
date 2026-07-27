package tikitaka.service.member.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.member.adapter.out.persistence.corporation.CorporationJpaEntity;
import tikitaka.service.member.adapter.out.persistence.corporation.CorporationJpaRepository;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaEntity;
import tikitaka.service.member.adapter.out.persistence.member.MemberJpaRepository;
import tikitaka.service.member.application.port.out.auth.AuthenticationAccountPort;
import tikitaka.service.member.domain.auth.AccountType;
import tikitaka.service.member.domain.auth.AuthenticationAccount;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationAccountPersistenceAdapter implements AuthenticationAccountPort {

	private final MemberJpaRepository memberJpaRepository;
	private final CorporationJpaRepository corporationJpaRepository;

	@Override
	public Optional<AuthenticationAccount> findMemberByUsername(String username) {

		return memberJpaRepository.findByUsername(username)
				.map(this::toMemberAuthenticationAccount);
	}

	@Override
	public Optional<AuthenticationAccount> findCorporationByUsername(String username) {

		return corporationJpaRepository.findByUsername(username)
				.map(this::toCorporationAuthenticationAccount);
	}

	private AuthenticationAccount toMemberAuthenticationAccount(
			MemberJpaEntity memberJpaEntity
	) {

		return new AuthenticationAccount(
				memberJpaEntity.getId(),
				memberJpaEntity.getUsername(),
				memberJpaEntity.getPassword(),
				AccountType.MEMBER
		);
	}

	private AuthenticationAccount toCorporationAuthenticationAccount(
			CorporationJpaEntity corporationJpaEntity
	) {

		return new AuthenticationAccount(
				corporationJpaEntity.getId(),
				corporationJpaEntity.getUsername(),
				corporationJpaEntity.getPassword(),
				AccountType.CORPORATION
		);
	}
}
