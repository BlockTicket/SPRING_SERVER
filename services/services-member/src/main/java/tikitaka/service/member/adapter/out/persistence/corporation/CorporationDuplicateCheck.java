package tikitaka.service.member.adapter.out.persistence.corporation;

public interface CorporationDuplicateCheck {

	Long getUsernameExists();
	Long getEmailExists();
	Long getPhoneExists();
}
