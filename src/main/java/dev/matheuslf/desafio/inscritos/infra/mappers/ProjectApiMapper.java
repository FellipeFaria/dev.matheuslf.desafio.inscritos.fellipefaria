package dev.matheuslf.desafio.inscritos.infra.mappers;

import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Name;
import dev.matheuslf.desafio.inscritos.infra.dtos.ProjectRequestDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.ProjectResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectApiMapper {
    private final TaskApiMapper taskApiMapper;

    public ProjectApiMapper(TaskApiMapper taskApiMapper) {
        this.taskApiMapper = taskApiMapper;
    }

    public Project toEntity(ProjectRequestDTO dto) {
        return new Project(
                new Name(dto.name()),
                new Description(dto.description()),
                new Date(dto.startDate()),
                new Date(dto.endDate())
        );
    }

    public ProjectResponseDTO toResponse(Project entity) {
        var tasks = entity.getTasks()
                .stream()
                .map(taskApiMapper::toResponse)
                .toList();

        return new ProjectResponseDTO(
                entity.getId(),
                entity.getName().value(),
                entity.getDescription().value(),
                entity.getStartDate().value(),
                entity.getEndDate().value(),
                tasks
        );
    }
}
