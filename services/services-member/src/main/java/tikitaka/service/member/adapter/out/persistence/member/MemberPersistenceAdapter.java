package tikitaka.service.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.member.application.port.out.SaveMemberPort;
import tikitaka.service.member.domain.exception.exception.EmailAlreadyExist;
import tikitaka.service.member.domain.exception.exception.PhoneAlreadyExist;
import tikitaka.service.member.domain.exception.exception.UsernameAlreadyExist;
import tikitaka.service.member.domain.member.Member;

@Component
@RestControllerAdvice
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SaveMemberPort {

	private final MemberJpaRepository memberJpaRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@ExceptionHandler(CommonException.class)
	public void saveMember(Member member) {


		if (memberJpaRepository.existsByUsername(member.getUsername())) throw new UsernameAlreadyExist();
		else if (memberJpaRepository.existsByEmail(member.getEmail())) throw new EmailAlreadyExist();
		else if (memberJpaRepository.existsByPhone(member.getPhone())) throw new PhoneAlreadyExist();

		memberJpaRepository.save(MemberJpaEntity.builder()
				.id(member.getId())
				.username(member.getUsername())
				.email(member.getEmail())
				.phone(member.getPhone())
				.password(passwordEncoder.encode(member.getPassword())) // 비밀번호 평문으로 들어감
				.userType(member.getUserType())
				.provider(member.getProvider())
				.build()
		);
	}
}
