package tikitaka.service.member.adapter.out.persistence.member;

public interface DuplicateCheck {

	Long getUsernameExists();
	Long getEmailExists();
	Long getPhoneExists();
}