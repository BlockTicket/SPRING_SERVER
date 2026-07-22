package tikitaka.service.member.adapter.out.persistence.corporation;

public interface DuplicateCheck {

	Long getUsernameExists();
	Long getEmailExists();
	Long getPhoneExists();
}
