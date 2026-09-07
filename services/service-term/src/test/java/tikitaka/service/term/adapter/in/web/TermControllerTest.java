package tikitaka.service.term.adapter.in.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tikitaka.core.common.handler.GlobalExceptionHandler;
import tikitaka.service.term.application.port.in.CreateTermUseCase;
import tikitaka.service.term.application.port.in.DeleteTermUseCase;
import tikitaka.service.term.application.port.in.GetTermUseCase;
import tikitaka.service.term.application.port.in.GetTermsUseCase;
import tikitaka.service.term.application.port.in.UpdateTermUseCase;
import tikitaka.service.term.application.port.in.data.CreateTermCommand;
import tikitaka.service.term.application.port.in.data.UpdateTermCommand;
import tikitaka.service.term.domain.Term;
import tikitaka.service.term.domain.exception.TermNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TermControllerTest {

	@Mock
	private CreateTermUseCase createTermUseCase;
	@Mock
	private GetTermUseCase getTermUseCase;
	@Mock
	private GetTermsUseCase getTermsUseCase;
	@Mock
	private UpdateTermUseCase updateTermUseCase;
	@Mock
	private DeleteTermUseCase deleteTermUseCase;
	@InjectMocks
	private TermController termController;

	private MockMvc mockMvc;
	private final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(termController)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	void createTermUsingSnakeCaseRequest() throws Exception {
		CreateTermCommand command = new CreateTermCommand("서비스 이용약관", "약관 내용");
		when(createTermUseCase.createTerm(command)).thenReturn(id);

		mockMvc.perform(post("/api/term").contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"term_title":"서비스 이용약관","term_content":"약관 내용"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.httpStatus").value(200))
				.andExpect(jsonPath("$.data.url").value("/api/term/" + id));
		verify(createTermUseCase).createTerm(command);
	}

	@Test
	void rejectBlankTitle() throws Exception {
		mockMvc.perform(post("/api/term").contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"term_title":" ","term_content":"약관 내용"}
						"""))
				.andExpect(status().isBadRequest());
		verifyNoInteractions(createTermUseCase);
	}

	@Test
	void getAllTerms() throws Exception {
		when(getTermsUseCase.getTerms()).thenReturn(List.of(Term.of(id, "제목", "내용")));
		mockMvc.perform(get("/api/terms"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].id").value(id.toString()))
				.andExpect(jsonPath("$.data[0].term_title").value("제목"))
				.andExpect(jsonPath("$.data[0].term_content").value("내용"));
	}

	@Test
	void getSingleTerm() throws Exception {
		when(getTermUseCase.getTerm(id)).thenReturn(Term.of(id, "제목", "내용"));
		mockMvc.perform(get("/api/term/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(id.toString()))
				.andExpect(jsonPath("$.data.term_title").value("제목"));
	}

	@Test
	void returnNotFoundForMissingTerm() throws Exception {
		when(getTermUseCase.getTerm(id)).thenThrow(new TermNotFoundException());
		mockMvc.perform(get("/api/term/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.httpStatus").value(404))
				.andExpect(jsonPath("$.code").value("TERM_NOT_FOUND"));
	}

	@Test
	void updateTermUsingBodyId() throws Exception {
		mockMvc.perform(patch("/api/term").contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"id":"%s","term_title":"수정 제목","term_content":"수정 내용"}
						""".formatted(id)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.url").value("/api/term/" + id));
		verify(updateTermUseCase).updateTerm(new UpdateTermCommand(id, "수정 제목", "수정 내용"));
	}

	@Test
	void rejectUpdateWithoutId() throws Exception {
		mockMvc.perform(patch("/api/term").contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"term_title":"수정 제목","term_content":"수정 내용"}
						"""))
				.andExpect(status().isBadRequest());
		verifyNoInteractions(updateTermUseCase);
	}

	@Test
	void deleteTermUsingPathId() throws Exception {
		mockMvc.perform(delete("/api/term/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.httpStatus").value(200))
				.andExpect(jsonPath("$.data").doesNotExist());
		verify(deleteTermUseCase).deleteTerm(id);
	}
}
