package dev.matheuslf.desafio.inscritos.application;

import dev.matheuslf.desafio.inscritos.application.exceptions.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.application.exceptions.TaskNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.implementions.*;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskUseCasesTest {

    @Mock
    private TaskGateway taskGateway;
    @Mock
    private ProjectGateway projectGateway;

    @InjectMocks private DoATaskUseCaseImpl doATaskUseCase;
    @InjectMocks private DoneATaskUseCaseImpl doneATaskUseCase;
    @InjectMocks private DeleteTaskUseCaseImpl deleteTaskUseCase;
    @InjectMocks private FindAllTasksUseCaseImpl findAllTasksUseCase;
    @InjectMocks private FindTaskByIdUseCaseImpl findTaskByIdUseCase;

    @Test
    @DisplayName("DoATask: Deve iniciar tarefa e salvar")
    void shouldStartTask() {
        Long taskId = 1L;
        Task task = Mockito.mock(Task.class);
        when(taskGateway.findTaskById(taskId)).thenReturn(task);
        when(taskGateway.save(task)).thenReturn(task);

        doATaskUseCase.execute(taskId);

        verify(task).doTask(); // Verifica se chamou o método de domínio
        verify(taskGateway).save(task);
    }

    @Test
    @DisplayName("DoneATask: Deve concluir tarefa e salvar")
    void shouldFinishTask() {
        Long taskId = 1L;
        Task task = Mockito.mock(Task.class);
        when(taskGateway.findTaskById(taskId)).thenReturn(task);
        when(taskGateway.save(task)).thenReturn(task);

        doneATaskUseCase.execute(taskId);

        verify(task).doneTask();
        verify(taskGateway).save(task);
    }

    @Test
    @DisplayName("DeleteTask: Deve remover tarefa do projeto e salvar projeto")
    void shouldDeleteTask() {
        Long taskId = 1L;
        Long projectId = 10L;

        Task task = Mockito.mock(Task.class);
        when(task.getProjectId()).thenReturn(projectId);

        Project project = Mockito.mock(Project.class);

        when(taskGateway.findTaskById(taskId)).thenReturn(task);
        when(projectGateway.findProjectById(projectId)).thenReturn(project);

        deleteTaskUseCase.execute(taskId);

        verify(project).removeTask(task); // Verifica se removeu da lista do projeto
        verify(projectGateway).save(project); // Verifica se salvou o projeto atualizado
    }

    @Test
    @DisplayName("DeleteTask: Deve lançar erro se tarefa não existe")
    void shouldThrowWhenDeletingNonExistentTask() {
        when(taskGateway.findTaskById(any())).thenReturn(null);
        Assertions.assertThrows(TaskNotFoundException.class, () -> deleteTaskUseCase.execute(1L));
    }

    @Test
    @DisplayName("FindAllTasks: Deve chamar gateway com filtro")
    void shouldFindAllTasks() {
        TaskFilter filter = new TaskFilter(1L, null, null);
        when(taskGateway.findAll(filter)).thenReturn(List.of());

        findAllTasksUseCase.execute(filter);

        verify(taskGateway).findAll(filter);
    }

    @Test
    @DisplayName("FindTaskById: Deve retornar tarefa se existir")
    void shouldFindTaskById() {
        Task task = Mockito.mock(Task.class);
        when(taskGateway.findTaskById(1L)).thenReturn(task);

        Task result = findTaskByIdUseCase.execute(1L);
        Assertions.assertNotNull(result);
    }
}