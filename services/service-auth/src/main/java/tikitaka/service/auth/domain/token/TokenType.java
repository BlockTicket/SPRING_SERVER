package tikitaka.service.auth.domain.token;

public enum TokenType {

	ACCESS_TOKEN("accessToken"),
	REFRESH_TOKEN("refreshToken");

	private final String claim;

	TokenType(
			String claim
	) {

		this.claim = claim;
	}

	public String claim() {

		return this.claim;
	}
}
