package tikitaka.service.member.adapter.out.persistence.external.nts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface NtsBusinessJpaRepository extends JpaRepository<NtsBusinessJpaEntity, UUID> {

	@Query(value = """
	SELECT
		EXISTS (
			SELECT 1 FROM business_license
			WHERE b_no = :b_no
			) AS BNoExists
	""", nativeQuery = true)
	NtsDuplicationCheck ntsDuplicationCheck(
			@Param("b_no") String bNo
	);
}
