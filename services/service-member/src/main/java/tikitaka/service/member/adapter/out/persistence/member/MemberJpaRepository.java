package tikitaka.service.member.adapter.out.persistence.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tikitaka.service.member.domain.member.MemberType;

import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {

	@Query(value = """
	SELECT
		EXISTS (
			SELECT 1 FROM member
			WHERE username = :username AND type = :type
		) AS usernameExists,
		EXISTS (
			SELECT 1 FROM member
			WHERE email = :email AND type = :type
		) AS emailExists,
		EXISTS (
			SELECT 1 FROM member
			WHERE phone = :phone AND type = :type
		) AS phoneExists
	""", nativeQuery = true)
	DuplicateCheck checkDuplicate(
			@Param("username") String username,
			@Param("email") String email,
			@Param("phone") String phone,
			@Param("type") String type
	);

	boolean existsByUsernameAndTypeAndIdNot(String username, MemberType type, UUID id);
}
