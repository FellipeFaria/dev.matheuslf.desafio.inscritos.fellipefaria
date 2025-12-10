package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.exceptions.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.application.exceptions.TaskNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;

public class DeleteTaskUseCaseImpl implements BaseUseCase<Long, Void> {
    private final TaskGateway taskGateway;
    private final ProjectGateway projectGateway;

    public DeleteTaskUseCaseImpl(TaskGateway taskGateway, ProjectGateway projectGateway) {
        this.taskGateway = taskGateway;
        this.projectGateway = projectGateway;
    }

    @Override
    public Void execute(Long id) {
        var task = this.taskGateway.findTaskById(id);

        if (task == null) {
            throw new TaskNotFoundException();
        }

        var project = this.projectGateway.findProjectById(task.getProjectId());

        if (project == null) {
            throw new ProjectNotFoundException();
        }

        project.removeTask(task);

        this.projectGateway.save(project);

        return null;
    }
    
}
