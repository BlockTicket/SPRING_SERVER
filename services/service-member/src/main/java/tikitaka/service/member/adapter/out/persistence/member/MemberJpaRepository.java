package tikitaka.service.member.adapter.out.persistence.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {

	@Query(value = """
	SELECT
		EXISTS (
			SELECT 1 FROM member
			WHERE username = :username
		) AS usernameExists,
		EXISTS (
			SELECT 1 FROM member
			WHERE email = :email
		) AS emailExists,
		EXISTS (
			SELECT 1 FROM member
			WHERE phone = :phone
		) AS phoneExists
	""", nativeQuery = true)
	DuplicateCheck checkDuplicate(
			@Param("username") String username,
			@Param("email") String email,
			@Param("phone") String phone
	);

	boolean existsByUsernameAndIdNot(String newUsername, UUID id);
}
