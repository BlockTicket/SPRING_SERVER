package tikitaka.service.term.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tikitaka.core.common.handler.GlobalExceptionHandler;
import tikitaka.service.term.adapter.in.web.TermController;
import tikitaka.service.term.application.port.in.*;
import tikitaka.service.term.domain.Term;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(TermSecurityTest.WebConfig.class)
@WebAppConfiguration
class TermSecurityTest {

	private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
	private static final String BODY = """
			{"id":"%s","term_title":"약관","term_content":"내용"}
			""".formatted(ID);

	@Autowired
	private WebApplicationContext context;
	@MockitoBean
	private CreateTermUseCase createTermUseCase;
	@MockitoBean
	private GetTermUseCase getTermUseCase;
	@MockitoBean
	private GetTermsUseCase getTermsUseCase;
	@MockitoBean
	private UpdateTermUseCase updateTermUseCase;
	@MockitoBean
	private DeleteTermUseCase deleteTermUseCase;

	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	void allowAnonymousReads() throws Exception {
		when(getTermsUseCase.getTerms()).thenReturn(List.of(Term.of(ID, "약관", "내용")));
		when(getTermUseCase.getTerm(ID)).thenReturn(Term.of(ID, "약관", "내용"));
		mvc.perform(get("/api/terms")).andExpect(status().isOk());
		mvc.perform(get("/api/term/{id}", ID)).andExpect(status().isOk());
		mvc.perform(head("/api/terms")).andExpect(status().isOk());
	}

	@ParameterizedTest
	@ValueSource(strings = {"POST", "PATCH", "DELETE"})
	void rejectAnonymousWrites(String method) throws Exception {
		mvc.perform(writeRequest(method))
				.andExpect(status().isUnauthorized())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.httpStatus").value(401))
				.andExpect(jsonPath("$.code").value("TERM_AUTHENTICATION_REQUIRED"));
		verifyNoInteractions(createTermUseCase, updateTermUseCase, deleteTermUseCase);
	}

	@ParameterizedTest
	@ValueSource(strings = {"POST", "PATCH", "DELETE"})
	void rejectOrdinaryUserWrites(String method) throws Exception {
		mvc.perform(writeRequest(method).with(user("member").roles("USER")))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.httpStatus").value(403))
				.andExpect(jsonPath("$.code").value("TERM_ACCESS_DENIED"));
		verifyNoInteractions(createTermUseCase, updateTermUseCase, deleteTermUseCase);
	}

	@ParameterizedTest
	@ValueSource(strings = {"POST", "PATCH", "DELETE"})
	void allowAdminWrites(String method) throws Exception {
		if (method.equals("POST")) when(createTermUseCase.createTerm(any())).thenReturn(ID);
		mvc.perform(writeRequest(method).with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk());
		switch (method) {
			case "POST" -> verify(createTermUseCase).createTerm(any());
			case "PATCH" -> verify(updateTermUseCase).updateTerm(any());
			case "DELETE" -> verify(deleteTermUseCase).deleteTerm(ID);
		}
	}

	@Test
	void doNotTrustClientSuppliedRoleOrToken() throws Exception {
		mvc.perform(writeRequest("POST")
				.header("X-Role", "ADMIN")
				.header("X-User-Role", "ROLE_ADMIN")
				.header("Authorization", "Bearer unverified-token"))
				.andExpect(status().isUnauthorized());
		verifyNoInteractions(createTermUseCase);
	}

	@Test
	void validateAdminInputAfterAuthorization() throws Exception {
		mvc.perform(post("/api/term").with(user("admin").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"term_title\":\" \",\"term_content\":\"내용\"}"))
				.andExpect(status().isBadRequest());
		verifyNoInteractions(createTermUseCase);
	}

	@Test
	void denyUnlistedWritePathsEvenForAdmin() throws Exception {
		mvc.perform(put("/api/term").with(user("admin").roles("ADMIN")))
				.andExpect(status().isForbidden());
		verifyNoInteractions(createTermUseCase, updateTermUseCase, deleteTermUseCase);
	}

	private MockHttpServletRequestBuilder writeRequest(String method) {
		String path = method.equals("DELETE") ? "/api/term/" + ID : "/api/term";
		return request(HttpMethod.valueOf(method), path).contentType(MediaType.APPLICATION_JSON).content(BODY);
	}

	@Configuration
	@EnableWebMvc
	@Import({TermSecurityConfig.class, TermController.class, GlobalExceptionHandler.class})
	static class WebConfig {
		@Bean
		ObjectMapper objectMapper() {
			return new ObjectMapper();
		}
	}
}
