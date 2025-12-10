package dev.matheuslf.desafio.inscritos.application;

import dev.matheuslf.desafio.inscritos.application.exceptions.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.implementions.CreateProjectUseCaseImpl;
import dev.matheuslf.desafio.inscritos.application.usecases.implementions.FindProjectByIdUseCaseImpl;
import dev.matheuslf.desafio.inscritos.application.usecases.implementions.ListAllProjectsUseCaseImpl;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectUseCasesTest {

    @Mock
    private ProjectGateway projectGateway;

    @InjectMocks
    private CreateProjectUseCaseImpl createProjectUseCase;
    @InjectMocks
    private ListAllProjectsUseCaseImpl listAllProjectsUseCase;
    @InjectMocks
    private FindProjectByIdUseCaseImpl findProjectByIdUseCase;

    @Test
    @DisplayName("Deve salvar projeto")
    void shouldCreateProject() {
        Project project = Mockito.mock(Project.class);
        when(projectGateway.save(project)).thenReturn(project);

        Project result = createProjectUseCase.execute(project);

        Assertions.assertNotNull(result);
        verify(projectGateway).save(project);
    }

    @Test
    @DisplayName("Deve listar projetos")
    void shouldListProjects() {
        when(projectGateway.listAllProjects()).thenReturn(List.of(Mockito.mock(Project.class)));

        List<Project> result = listAllProjectsUseCase.execute(null);

        Assertions.assertFalse(result.isEmpty());
        verify(projectGateway).listAllProjects();
    }

    @Test
    @DisplayName("Deve buscar projeto por ID existente")
    void shouldFindProjectById() {
        Project project = Mockito.mock(Project.class);
        when(projectGateway.findProjectById(1L)).thenReturn(project);

        Project result = findProjectByIdUseCase.execute(1L);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar projeto inexistente")
    void shouldThrowWhenProjectNotFound() {
        when(projectGateway.findProjectById(99L)).thenReturn(null);

        Assertions.assertThrows(ProjectNotFoundException.class, () ->
                findProjectByIdUseCase.execute(99L)
        );
    }
}