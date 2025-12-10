package dev.matheuslf.desafio.inscritos.infra.mappers;

import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Title;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskRequestDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskApiMapper {
    public Task toEntity(TaskRequestDTO dto) {
        return new Task(
                new Title(dto.title()),
                new Description(dto.description()),
                dto.priority(),
                new Date(dto.dueDate()),
                dto.projectId()
        );
    }

    public TaskResponseDTO toResponse(Task entity) {
        return new TaskResponseDTO(
                entity.getId(),
                entity.getTitle().value(),
                entity.getDescription().value(),
                entity.getTaskStatus(),
                entity.getTaskPriority(),
                entity.getDueDate().value(),
                entity.getProjectId()
        );
    }
}
