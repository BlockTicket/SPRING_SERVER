package tikitaka.service.member.adapter.out.persistence.external.nts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tikitaka.service.member.adapter.in.web.data.request.external.NtsVerifyRequest;
import tikitaka.service.member.adapter.in.web.data.response.external.NtsVerifyResponse;

import java.time.Duration;

@Component
public class NtsApiClient {

	private final RestClient restClient;
	private final String serviceKey;

	public NtsApiClient(
			@Value("${SERVICE_KEY}") String serviceKey
	) {

		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();

		simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(5));
		simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(10));

		this.serviceKey = serviceKey;
		this.restClient = RestClient.builder()
				.baseUrl("https://api.odcloud.kr/api/nts-businessman/v1")
				.requestFactory(simpleClientHttpRequestFactory)
				.build();
	}

	public NtsVerifyResponse validate(
			NtsVerifyRequest ntsVerifyRequest
	) {

		return restClient.post()
				.uri(uriBuilder -> uriBuilder
						.path("/validate")
						.queryParam("serviceKey", serviceKey)
						.build()
				)
				.contentType(MediaType.APPLICATION_JSON)
				.body(ntsVerifyRequest)
				.retrieve()
				.body(NtsVerifyResponse.class);
	}
}
