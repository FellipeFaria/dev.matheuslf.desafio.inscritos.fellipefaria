package dev.matheuslf.desafio.inscritos.domain.gateway;

import java.util.List;

import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;

public interface TaskGateway {
    Task save(Task task);
    Task findTaskById(Long id);
    List<Task> findAll(TaskFilter taskFilter);
}
