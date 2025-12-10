package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.exceptions.TaskNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Task;

public class DoneATaskUseCaseImpl implements BaseUseCase<Long, Task> {
    private final TaskGateway gateway;

    public DoneATaskUseCaseImpl(TaskGateway taskGateway) {
        this.gateway = taskGateway;
    }

    @Override
    public Task execute(Long id) {
        var task = this.gateway.findTaskById(id);

        if (task == null) {
            throw new TaskNotFoundException();
        }

        task.doneTask();

        return this.gateway.save(task);
    }
}
