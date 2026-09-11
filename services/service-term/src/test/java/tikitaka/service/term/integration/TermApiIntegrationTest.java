package tikitaka.service.term.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.config.import=",
				"spring.jpa.hibernate.ddl-auto=create",
				"server.address=127.0.0.1"
		}
)
class TermApiIntegrationTest {

	@Container
	static final MySQLContainer mysql = new MySQLContainer("mysql:8.4")
			.withDatabaseName("term_test")
			.withUsername("term_test")
			.withPassword("term_test_password");

	@DynamicPropertySource
	static void databaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
		registry.add("spring.datasource.username", mysql::getUsername);
		registry.add("spring.datasource.password", mysql::getPassword);
	}

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private HttpClient httpClient;

	@BeforeEach
	void setUp() {
		// The datasource always points to the disposable container above.
		jdbcTemplate.update("DELETE FROM term");
		httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
	}

	@AfterEach
	void tearDown() {
		httpClient.close();
	}

	@Test
	void createReadUpdateAndDeleteTermInMysql() throws Exception {
		String title = "가".repeat(255);
		String content = "한글 이용약관 내용\n".repeat(3000);

		HttpResponse<String> created = request("POST", "/api/term", Map.of(
				"term_title", title,
				"term_content", content
		));
		assertThat(created.statusCode()).isEqualTo(200);
		JsonNode createdBody = objectMapper.readTree(created.body());
		assertThat(createdBody.path("httpStatus").asInt()).isEqualTo(200);
		String termUrl = createdBody.path("data").path("url").asString();
		assertThat(termUrl).startsWith("/api/term/");
		UUID id = UUID.fromString(termUrl.substring("/api/term/".length()));

		assertThat(jdbcTemplate.queryForMap("SELECT title, content FROM term"))
				.containsEntry("title", title)
				.containsEntry("content", content);

		HttpResponse<String> detail = request("GET", termUrl, null);
		assertThat(detail.statusCode()).isEqualTo(200);
		JsonNode detailData = objectMapper.readTree(detail.body()).path("data");
		assertThat(detailData.path("id").asString()).isEqualTo(id.toString());
		assertThat(detailData.path("term_title").asString()).isEqualTo(title);
		assertThat(detailData.path("term_content").asString()).isEqualTo(content);

		HttpResponse<String> list = request("GET", "/api/terms", null);
		assertThat(list.statusCode()).isEqualTo(200);
		JsonNode listData = objectMapper.readTree(list.body()).path("data");
		assertThat(listData.size()).isEqualTo(1);
		assertThat(listData.get(0).path("id").asString()).isEqualTo(id.toString());

		HttpResponse<String> updated = request("PATCH", "/api/term", Map.of(
				"id", id.toString(),
				"term_title", "수정한 약관",
				"term_content", "수정한 내용"
		));
		assertThat(updated.statusCode()).isEqualTo(200);
		assertThat(objectMapper.readTree(updated.body()).path("data").path("url").asString())
				.isEqualTo(termUrl);
		assertThat(jdbcTemplate.queryForMap("SELECT title, content FROM term"))
				.containsEntry("title", "수정한 약관")
				.containsEntry("content", "수정한 내용");

		HttpResponse<String> deleted = request("DELETE", termUrl, null);
		assertThat(deleted.statusCode()).isEqualTo(200);
		assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM term", Integer.class)).isZero();

		HttpResponse<String> missing = request("GET", termUrl, null);
		assertThat(missing.statusCode()).isEqualTo(404);
		assertThat(objectMapper.readTree(missing.body()).path("code").asString()).isEqualTo("TERM_NOT_FOUND");
	}

	@Test
	void rejectOverlongTitleWithoutSavingToMysql() throws Exception {
		HttpResponse<String> response = request("POST", "/api/term", Map.of(
				"term_title", "가".repeat(256),
				"term_content", "약관 내용"
		));

		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM term", Integer.class)).isZero();
	}

	private HttpResponse<String> request(String method, String path, Map<String, String> body) throws Exception {
		HttpRequest.BodyPublisher publisher = body == null
				? HttpRequest.BodyPublishers.noBody()
				: HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body));
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path))
				.timeout(Duration.ofSeconds(10))
				.header("Content-Type", "application/json")
				.method(method, publisher)
				.build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
