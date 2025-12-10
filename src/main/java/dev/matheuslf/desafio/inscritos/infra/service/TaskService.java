package dev.matheuslf.desafio.inscritos.infra.service;

import dev.matheuslf.desafio.inscritos.domain.gateway.TaskGateway;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;
import dev.matheuslf.desafio.inscritos.infra.mappers.TaskMapper;
import dev.matheuslf.desafio.inscritos.infra.persistence.TaskEntity;
import dev.matheuslf.desafio.inscritos.infra.persistence.TaskEntityRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskService implements TaskGateway {
    private final TaskEntityRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskEntityRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {
        var jpaEntity = this.mapper.toEntity(task);

        var savedTask = this.repository.save(jpaEntity);

        return this.mapper.toDomain(savedTask);
    }

    @Override
    public Task findTaskById(Long id) {
        var jpaEntity = this.repository.findById(id).orElse(null);

        return this.mapper.toDomain(jpaEntity);
    }

    @Override
    public List<Task> findAll(TaskFilter taskFilter) {
        Specification<TaskEntity> specification = (
                root,
                query,
                cb
        ) -> cb.conjunction();

        if (taskFilter.projectId() != null) {
            specification = specification.and(
                    (root, query, cb) ->
                        cb.equal(root.get("project").get("id"), taskFilter.projectId())
            );
        }

        if (taskFilter.taskStatus() != null) {
            specification = specification.and(
                    (root, query, cb) ->
                            cb.equal(root.get("taskStatus"), taskFilter.taskStatus())
            );
        }

        if (taskFilter.taskPriority() != null) {
            specification = specification.and(
                    (root, query, cb) ->
                            cb.equal(root.get("taskPriority"), taskFilter.taskPriority())
            );
        }

        var jpaEntities = this.repository.findAll(specification);

        return jpaEntities.stream().map(this.mapper::toDomain).toList();
    }
}
