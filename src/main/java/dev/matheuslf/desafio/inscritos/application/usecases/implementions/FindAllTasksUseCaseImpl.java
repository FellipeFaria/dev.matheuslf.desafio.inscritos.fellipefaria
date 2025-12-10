package dev.matheuslf.desafio.inscritos.application.usecases.implementions;

import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;

import java.util.List;

public class FindAllTasksUseCaseImpl implements BaseUseCase<TaskFilter, List<Task>> {
    private final TaskGateway gateway;

    public FindAllTasksUseCaseImpl(TaskGateway taskGateway) {
        this.gateway = taskGateway;
    }

    @Override
    public List<Task> execute(TaskFilter taskFilter) {
        return this.gateway.findAll(taskFilter);
    }
}
