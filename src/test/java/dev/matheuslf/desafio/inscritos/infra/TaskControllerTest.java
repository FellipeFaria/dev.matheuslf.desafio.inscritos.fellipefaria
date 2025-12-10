package dev.matheuslf.desafio.inscritos.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskStatus;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskRequestDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskResponseDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskUpdateStatusDTO;
import dev.matheuslf.desafio.inscritos.infra.mappers.TaskApiMapper;
import dev.matheuslf.desafio.inscritos.infra.presentation.TaskController;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // --- Mocks dos UseCases (nomes iguais aos @Qualifier do Controller) ---

    @MockitoBean(name = "createTaskUseCaseImpl")
    private BaseUseCase<Task, Task> createTaskUseCase;

    @MockitoBean(name = "findAllTasksUseCaseImpl")
    private BaseUseCase<TaskFilter, List<Task>> findAllTasksUseCase;

    @MockitoBean(name = "doATaskUseCaseImpl")
    private BaseUseCase<Long, Task> doATaskUseCase;

    @MockitoBean(name = "doneATaskUseCaseImpl")
    private BaseUseCase<Long, Task> doneATaskUseCase;

    @MockitoBean(name = "deleteTaskUseCaseImpl")
    private BaseUseCase<Long, Void> deleteTaskUseCase;

    @MockitoBean
    private TaskApiMapper apiMapper;

    // ---------------------------------------------------------------------

    @Test
    @DisplayName("POST /task - Deve criar tarefa com sucesso (201 Created)")
    void shouldCreateTask() throws Exception {
        // Arrange
        TaskRequestDTO request = new TaskRequestDTO(
                "Nova Tarefa",
                "Descrição",
                TaskPriority.HIGH,
                LocalDateTime.now().plusDays(2),
                1L
        );

        Task mockTask = Mockito.mock(Task.class);
        TaskResponseDTO responseDTO = new TaskResponseDTO(
                10L, "Nova Tarefa", "Descrição", TaskStatus.TODO, TaskPriority.HIGH, request.dueDate(), 1L
        );

        // Simula o comportamento
        Mockito.when(apiMapper.toEntity(any())).thenReturn(mockTask);
        Mockito.when(createTaskUseCase.execute(any())).thenReturn(mockTask);
        Mockito.when(apiMapper.toResponse(any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Nova Tarefa"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    @DisplayName("PATCH /task/{id}/status - Deve atualizar para DOING (200 OK)")
    void shouldUpdateStatusToDoing() throws Exception {
        // Arrange
        Long taskId = 10L;
        TaskUpdateStatusDTO request = new TaskUpdateStatusDTO(TaskStatus.DOING);

        Task mockTask = Mockito.mock(Task.class);
        TaskResponseDTO responseDTO = new TaskResponseDTO(
                taskId, "Tarefa", "Desc", TaskStatus.DOING, TaskPriority.LOW, LocalDateTime.now(), 1L
        );

        // O Controller chama 'doATask' quando recebe DOING
        Mockito.when(doATaskUseCase.execute(taskId)).thenReturn(mockTask);
        Mockito.when(apiMapper.toResponse(mockTask)).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(patch("/task/{id}/status", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DOING"));

        // Verifica se chamou o UseCase correto
        Mockito.verify(doATaskUseCase).execute(taskId);
        Mockito.verify(doneATaskUseCase, Mockito.never()).execute(any());
    }

    @Test
    @DisplayName("PATCH /task/{id}/status - Deve atualizar para DONE (200 OK)")
    void shouldUpdateStatusToDone() throws Exception {
        Long taskId = 10L;
        TaskUpdateStatusDTO request = new TaskUpdateStatusDTO(TaskStatus.DONE);

        Task mockTask = Mockito.mock(Task.class);
        TaskResponseDTO responseDTO = new TaskResponseDTO(
                taskId, "Tarefa", "Desc", TaskStatus.DONE, TaskPriority.LOW, LocalDateTime.now(), 1L
        );

        // O Controller chama 'doneATask' quando recebe DONE
        Mockito.when(doneATaskUseCase.execute(taskId)).thenReturn(mockTask);
        Mockito.when(apiMapper.toResponse(mockTask)).thenReturn(responseDTO);

        mockMvc.perform(patch("/task/{id}/status", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    @DisplayName("DELETE /task/{id} - Deve deletar tarefa (200 OK)")
    void shouldDeleteTask() throws Exception {
        Long taskId = 5L;

        mockMvc.perform(delete("/task/{id}", taskId))
                .andExpect(status().isOk());

        Mockito.verify(deleteTaskUseCase).execute(taskId);
    }

    @Test
    @DisplayName("GET /task - Deve retornar lista filtrada (200 OK)")
    void shouldFindAllTasksWithFilter() throws Exception {
        // Arrange
        // Mocka o retorno de lista vazia ou preenchida
        Mockito.when(findAllTasksUseCase.execute(any(TaskFilter.class)))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        // Testa passando query params (ex: ?priority=HIGH)
        mockMvc.perform(get("/task")
                        .param("priority", "HIGH")
                        .param("status", "TODO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Verifica se o filtro foi montado corretamente (opcional, mas bom pra garantir)
        Mockito.verify(findAllTasksUseCase).execute(any(TaskFilter.class));
    }
}