package tikitaka.service.gateway;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.import=")
class TermGatewayTest {

    private static final String RESPONSE_BODY = "{\"message\":\"약관 응답\"}";
    private static final BlockingQueue<ReceivedRequest> requests = new LinkedBlockingQueue<>();
    private static final AtomicInteger responseStatus = new AtomicInteger(200);
    private static final HttpServer downstream = startDownstream();

    @LocalServerPort
    private int port;

    @Autowired
    private Environment environment;

    private HttpClient client;

    @DynamicPropertySource
    static void serviceAddresses(DynamicPropertyRegistry registry) {
        String uri = "http://127.0.0.1:" + downstream.getAddress().getPort();
        registry.add("SERVICE_TERM_URI", () -> uri);
        registry.add("SERVICE_MEMBER_URI", () -> uri);
        registry.add("SERVICE_FILE_URI", () -> uri);
    }

    @BeforeEach
    void setUp() {
        requests.clear();
        responseStatus.set(200);
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    }

    @AfterEach
    void tearDown() {
        client.close();
    }

    @AfterAll
    static void stopDownstream() {
        downstream.stop(0);
    }

    @Test
    void bindDevGatewayToLoopbackAddress() {
        assertThat(environment.getProperty("server.address")).isEqualTo("127.0.0.1");
    }

    @ParameterizedTest(name = "{0} {1}")
    @MethodSource("termRequests")
    void forwardTermRequests(String method, String path, String body) throws Exception {
        HttpResponse<String> response = send(method, "/term" + path, body);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(RESPONSE_BODY);
        assertThat(response.headers().firstValue("Content-Type").orElseThrow()).contains("application/json");
        assertThat(requests.remove()).isEqualTo(new ReceivedRequest(method, path, body));
        assertThat(requests).isEmpty();
    }

    static Stream<Arguments> termRequests() {
        String id = "7b763215-83b3-4c69-989f-b3fbb410955f";
        return Stream.of(
                Arguments.of("POST", "/api/term", "{\"term_title\":\"약관\",\"term_content\":\"내용\"}"),
                Arguments.of("GET", "/api/terms", ""),
                Arguments.of("GET", "/api/term/" + id, ""),
                Arguments.of("PATCH", "/api/term", "{\"id\":\"" + id + "\",\"term_title\":\"수정\",\"term_content\":\"내용\"}"),
                Arguments.of("DELETE", "/api/term/" + id, ""),
                Arguments.of("GET", "/api/terms?probe=gateway", "")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 404})
    void preserveDownstreamErrorResponses(int status) throws Exception {
        responseStatus.set(status);

        HttpResponse<String> response = send("GET", "/term/api/term/missing", "");

        assertThat(response.statusCode()).isEqualTo(status);
        assertThat(response.body()).isEqualTo(RESPONSE_BODY);
        assertThat(requests).hasSize(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"member", "file"})
    void preserveExistingServiceRoutes(String service) throws Exception {
        HttpResponse<String> response = send("GET", "/" + service + "/api/health", "");

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(requests.remove()).isEqualTo(new ReceivedRequest("GET", "/api/health", ""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/terms", "/terms/api/terms", "/terminal/api/terms"})
    void doNotForwardUnmatchedPaths(String path) throws Exception {
        assertThat(send("GET", path, "").statusCode()).isEqualTo(404);
        assertThat(requests).isEmpty();
    }

    private HttpResponse<String> send(String method, String path, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .method(method, body.isEmpty() ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpServer startDownstream() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/", exchange -> {
                try (exchange) {
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    requests.add(new ReceivedRequest(exchange.getRequestMethod(), exchange.getRequestURI().toString(), body));
                    byte[] response = RESPONSE_BODY.getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(responseStatus.get(), response.length);
                    exchange.getResponseBody().write(response);
                }
            });
            server.start();
            return server;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private record ReceivedRequest(String method, String path, String body) {
    }
}
