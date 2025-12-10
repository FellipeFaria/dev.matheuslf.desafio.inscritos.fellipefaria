package dev.matheuslf.desafio.inscritos.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.infra.dtos.ProjectRequestDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.ProjectResponseDTO;
import dev.matheuslf.desafio.inscritos.infra.mappers.ProjectApiMapper;
import dev.matheuslf.desafio.inscritos.infra.presentation.ProjectController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class) //
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Mockamos os Beans que o Controller usa
    @MockitoBean(name = "createProjectUseCaseImpl")
    private BaseUseCase<Project, Project> createProjectUseCase;

    @MockitoBean(name = "listAllProjectsUseCaseImpl")
    private BaseUseCase<Void, List<Project>> listAllProjectsUseCase;

    @MockitoBean
    private ProjectApiMapper apiMapper;

    @Test
    @DisplayName("POST /project - Deve retornar 201 Created quando válido")
    void shouldCreateProject() throws Exception {
        // Arrange
        ProjectRequestDTO request = new ProjectRequestDTO(
                "Projeto Teste",
                "Descricao",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5)
        );

        Project mockProject = Mockito.mock(Project.class);
        ProjectResponseDTO responseDTO = new ProjectResponseDTO(
                1L, "Projeto Teste", "Descricao", request.startDate(), request.endDate(), Collections.emptyList()
        );

        Mockito.when(apiMapper.toEntity(any())).thenReturn(mockProject);
        Mockito.when(createProjectUseCase.execute(any())).thenReturn(mockProject);
        Mockito.when(apiMapper.toResponse(any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Projeto Teste"));
    }

    @Test
    @DisplayName("GET /project - Deve retornar 200 OK com lista")
    void shouldReturnList() throws Exception {
        // Arrange
        Mockito.when(listAllProjectsUseCase.execute(null)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/project"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}