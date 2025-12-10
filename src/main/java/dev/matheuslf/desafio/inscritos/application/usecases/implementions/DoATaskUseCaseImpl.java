package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.exceptions.TaskNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Task;

public class DoATaskUseCaseImpl implements BaseUseCase<Long, Task> {
    private final TaskGateway gateway;

    public DoATaskUseCaseImpl(TaskGateway taskGateway) {
        this.gateway = taskGateway;
    }

    @Override
    public Task execute(Long id) {
        var task = this.gateway.findTaskById(id);

        if (task == null) {
            throw new TaskNotFoundException();
        }

        task.doTask();

        return this.gateway.save(task);
    }
}
