package dev.matheuslf.desafio.inscritos.infra.mappers;

import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Title;
import dev.matheuslf.desafio.inscritos.infra.persistence.ProjectEntity;
import dev.matheuslf.desafio.inscritos.infra.persistence.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskEntity toEntity(Task domain) {
        if (domain == null) return null;

       TaskEntity taskEntity = new TaskEntity();

       taskEntity.setId(domain.getId());
       taskEntity.setTitle(domain.getTitle().value());
       taskEntity.setDescription(domain.getDescription().value());
       taskEntity.setTaskStatus(domain.getTaskStatus());
       taskEntity.setTaskPriority(domain.getTaskPriority());
       taskEntity.setDueDate(domain.getDueDate().value());

       if (domain.getProjectId() != null) {
           ProjectEntity projectEntity = new ProjectEntity();
           projectEntity.setId(domain.getProjectId());
           taskEntity.setProject(projectEntity);
       }

       return taskEntity;
    }

    public Task toDomain(TaskEntity entity) {
        if (entity == null) return null;

        return new Task(
                entity.getId(),
                new Title(entity.getTitle()),
                new Description(entity.getDescription()),
                entity.getTaskStatus(),
                entity.getTaskPriority(),
                new Date(entity.getDueDate()),
                entity.getProject().getId()
        );
    }
}
