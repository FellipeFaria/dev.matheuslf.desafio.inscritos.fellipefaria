package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.exceptions.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.ProjectGateway;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Task;

public class CreateTaskUseCaseImpl implements BaseUseCase<Task, Task> {
    private final TaskGateway taskGateway;
    private final ProjectGateway projectGateway;

    public CreateTaskUseCaseImpl(TaskGateway taskGateway, ProjectGateway projectGateway) {
        this.taskGateway = taskGateway;
        this.projectGateway = projectGateway;
    }

    @Override
    public Task execute(Task task) {
        var project = this.projectGateway.findProjectById(task.getProjectId());

        if (project == null) {
            throw new ProjectNotFoundException();
        }

        project.addTask(task);

        return this.taskGateway.save(task);
    }
 
}
