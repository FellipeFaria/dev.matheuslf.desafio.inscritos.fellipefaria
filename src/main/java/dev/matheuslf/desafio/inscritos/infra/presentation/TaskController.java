package dev.matheuslf.desafio.inscritos.infra.presentation;

import dev.matheuslf.desafio.inscritos.application.usecases.interfaces.BaseUseCase;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskStatus;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.model.filter.TaskFilter;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskRequestDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskResponseDTO;
import dev.matheuslf.desafio.inscritos.infra.dtos.TaskUpdateStatusDTO;
import dev.matheuslf.desafio.inscritos.infra.mappers.TaskApiMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskApiMapper apiMapper;
    private final BaseUseCase<Task, Task> createTask;
    private final BaseUseCase<TaskFilter, List<Task>> findAll;
    private final BaseUseCase<Long, Task> doATask;
    private final BaseUseCase<Long, Task> doneATask;
    private final BaseUseCase<Long, Void> deleteTask;

    public TaskController(
            TaskApiMapper apiMapper,
            @Qualifier("createTaskUseCaseImpl") BaseUseCase<Task, Task> createTask,
            @Qualifier("findAllTasksUseCaseImpl") BaseUseCase<TaskFilter, List<Task>> findAll,
            @Qualifier("doATaskUseCaseImpl") BaseUseCase<Long, Task> doATask,
            @Qualifier("doneATaskUseCaseImpl") BaseUseCase<Long, Task> doneATask,
            @Qualifier("deleteTaskUseCaseImpl") BaseUseCase<Long, Void> deleteTask
    ) {
        this.apiMapper = apiMapper;
        this.createTask = createTask;
        this.findAll = findAll;
        this.doATask = doATask;
        this.doneATask = doneATask;
        this.deleteTask = deleteTask;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(
            @RequestBody @Valid TaskRequestDTO body
    ) {
        Task fromDTO = this.apiMapper.toEntity(body);

        Task response = this.createTask.execute(fromDTO);

        TaskResponseDTO responseDTO = this.apiMapper.toResponse(response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid TaskUpdateStatusDTO body
    ) {
        Task taskUpdated = switch (body.newStatus()) {
            case DONE -> this.doneATask.execute(id);
            case DOING -> this.doATask.execute(id);
            case TODO -> throw new IllegalArgumentException("Uma tarefa não pode voltar para TODO");
        };

        TaskResponseDTO response = this.apiMapper.toResponse(taskUpdated);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        this.deleteTask.execute(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority
    ) {
        TaskFilter taskFilter = new TaskFilter(projectId, status, priority);

        List<Task> tasks = this.findAll.execute(taskFilter);

        List<TaskResponseDTO> response = tasks.stream().map(this.apiMapper::toResponse).toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
