package tikitaka.service.member.adapter.in.web.security;

public final class TokenHeaders {

	public static final String BEARER_PREFIX = "Bearer ";
	public static final String REFRESH_TOKEN = "Refresh-Token";

	private TokenHeaders() {
	}

	public static boolean hasBearerPrefix(String value) {

		return value != null && value.startsWith(BEARER_PREFIX);
	}

	public static String withoutBearerPrefix(String value) {

		return value.substring(BEARER_PREFIX.length());
	}

	public static String bearer(String token) {

		return BEARER_PREFIX + token;
	}
}
