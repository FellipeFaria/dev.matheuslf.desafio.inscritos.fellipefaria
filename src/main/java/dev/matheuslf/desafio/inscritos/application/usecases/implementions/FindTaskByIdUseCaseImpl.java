package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.exceptions.TaskNotFoundException;
import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Task;

public class FindTaskByIdUseCaseImpl implements BaseUseCase<Long, Task> {
    private final TaskGateway gateway;

    public FindTaskByIdUseCaseImpl(TaskGateway taskGateway) {
        this.gateway = taskGateway;
    }

    @Override
    public Task execute(Long id) {
        var existent = this.gateway.findTaskById(id);

        if (existent == null) {
            throw new TaskNotFoundException();
        }

        return existent;
    }
    
}
