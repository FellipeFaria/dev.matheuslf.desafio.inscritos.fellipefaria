package dev.matheuslf.desafio.inscritos.infra.mappers;

import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Name;
import dev.matheuslf.desafio.inscritos.infra.persistence.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    private final TaskMapper taskMapper;

    public ProjectMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public ProjectEntity toEntity(Project domain) {
        if (domain == null) return null;

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setId(domain.getId());
        projectEntity.setName(domain.getName().value());
        projectEntity.setDescription(domain.getDescription().value());
        projectEntity.setStartDate(domain.getStartDate().value());
        projectEntity.setEndDate(domain.getEndDate().value());

        if (domain.getTasks() != null) {
            var tasks = domain.getTasks().stream()
                    .map(taskMapper::toEntity)
                    .toList();

            projectEntity.setTasks(tasks);
        }

        return projectEntity;
    }

    public Project toDomain(ProjectEntity entity) {
        if (entity == null) return null;

        Project project = new Project(
          entity.getId(),
          new Name(entity.getName()),
          new Description(entity.getDescription()),
          new Date(entity.getStartDate()),
          new Date(entity.getEndDate())
        );

        if (entity.getTasks() != null) {
            entity.getTasks().forEach(t ->
                    project.addTask(taskMapper.toDomain(t))
            );
        }

        return project;
    }
}
