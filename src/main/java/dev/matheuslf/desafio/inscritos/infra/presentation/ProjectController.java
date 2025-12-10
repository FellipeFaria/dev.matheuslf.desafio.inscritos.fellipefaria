package dev.matheuslf.desafio.inscritos.infra.presentation;

import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.infra.dtos.ProjectRequestDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.ProjectResponseDTO;
import dev.matheuslf.desafio.inscritos.infra.mappers.ProjectApiMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectApiMapper apiMapper;
    private final BaseUseCase<Project, Project> createProject;
    private final BaseUseCase<Void, List<Project>> listAllProjects;

    public ProjectController(
            ProjectApiMapper apiMapper,
            @Qualifier("createProjectUseCaseImpl") BaseUseCase<Project, Project> createProject,
            @Qualifier("listAllProjectsUseCaseImpl") BaseUseCase<Void, List<Project>> listAllProjects
    ) {
        this.apiMapper = apiMapper;
        this.createProject = createProject;
        this.listAllProjects = listAllProjects;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(
            @RequestBody @Valid ProjectRequestDTO body
    ) {
        Project fromDTO = this.apiMapper.toEntity(body);

        Project project = this.createProject.execute(fromDTO);

        ProjectResponseDTO responseDTO = this.apiMapper.toResponse(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<Project> projects = this.listAllProjects.execute(null);

        List<ProjectResponseDTO> response = projects.stream()
                                                .map(this.apiMapper::toResponse)
                                                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
