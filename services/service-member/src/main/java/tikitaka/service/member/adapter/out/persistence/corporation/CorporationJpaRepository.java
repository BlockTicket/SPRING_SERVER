package tikitaka.service.member.adapter.out.persistence.corporation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.Optional;

public interface CorporationJpaRepository extends JpaRepository<CorporationJpaEntity, UUID> {

	Optional<CorporationJpaEntity> findByUsername(String username);

	@Query(value = """
	SELECT
		EXISTS (
			SELECT 1 FROM corporation
			WHERE username = :username
		) AS usernameExists,
		EXISTS (
			SELECT 1 FROM corporation
			WHERE email = :email
		) AS emailExists,
		EXISTS (
			SELECT 1 FROM corporation
			WHERE phone = :phone
		) AS phoneExists
	""", nativeQuery = true)
	CorporationDuplicateCheck corporationDuplicateCheck(
			@Param("username") String username,
			@Param("email") String email,
			@Param("phone") String phone
	);
}
