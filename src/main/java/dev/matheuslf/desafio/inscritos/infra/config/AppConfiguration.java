package dev.matheuslf.desafio.inscritos.infra.config;

import dev.matheuslf.desafio.inscritos.application.usecases.implementions.*;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfiguration {
    @Bean("createProjectUseCaseImpl")
    public BaseUseCase<Project, Project> createProjectUseCase(ProjectGateway gateway) {
        return new CreateProjectUseCaseImpl(gateway);
    }

    @Bean("listAllProjectsUseCaseImpl")
    public BaseUseCase<Void, List<Project>> listAllProjectsUseCase(ProjectGateway projectGateway) {
        return new ListAllProjectsUseCaseImpl(projectGateway);
    }

    @Bean("createTaskUseCaseImpl")
    public BaseUseCase<Task, Task> createTaskUseCase(
            ProjectGateway projectGateway,
            TaskGateway taskGateway
    ) {
        return new CreateTaskUseCaseImpl(taskGateway, projectGateway);
    }

    @Bean("deleteTaskUseCaseImpl")
    public BaseUseCase<Long, Void> deleteTaskUseCase(
            TaskGateway taskGateway,
            ProjectGateway projectGateway
    ) {
        return new DeleteTaskUseCaseImpl(taskGateway, projectGateway);
    }

    @Bean("doATaskUseCaseImpl")
    public BaseUseCase<Long, Task> doATaskUseCase(TaskGateway taskGateway) {
        return new DoATaskUseCaseImpl(taskGateway);
    }

    @Bean("doneATaskUseCaseImpl")
    public BaseUseCase<Long, Task> doneATaskUseCase(TaskGateway taskGateway) {
        return new DoneATaskUseCaseImpl(taskGateway);
    }

    @Bean("findAllTasksUseCaseImpl")
    public BaseUseCase<TaskFilter, List<Task>> findAllTasksUseCase(TaskGateway taskGateway) {
        return new FindAllTasksUseCaseImpl(taskGateway);
    }
}
