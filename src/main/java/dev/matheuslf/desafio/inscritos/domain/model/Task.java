package dev.matheuslf.desafio.inscritos.domain.model;

import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskStatus;
import dev.matheuslf.desafio.inscritos.domain.exceptions.TaskStatusException;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Title;
import lombok.Getter;

@Getter
public class Task {
    private Long id;
    private final Title title;
    private final Description description;
    private TaskStatus taskStatus;
    private final TaskPriority taskPriority;
    private final Date dueDate;
    private final Long projectId;

    public Task (
        Title title, 
        Description description, 
        TaskPriority taskPriority,
        Date dueDate,
        Long projectId
    ) {
        this.title = title;
        this.description = description;
        this.taskStatus = TaskStatus.TODO;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.projectId = projectId;
    }

    public Task (
        Long id,
        Title title, 
        Description description, 
        TaskStatus taskStatus,
        TaskPriority taskPriority,
        Date dueDate,
        Long projectId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskStatus = taskStatus;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.projectId = projectId;
    }

    public void doTask() {
        if (this.taskStatus != TaskStatus.TODO) {
            throw new TaskStatusException("Não é possivel alterar status de uma tarefa com status diferente que TODO");
        }

        this.taskStatus = TaskStatus.DOING;
    }

    public void doneTask() {
        if (this.taskStatus != TaskStatus.DOING) {
            throw new TaskStatusException("Não é possivel alterar o status de uma tarefa com status diferente que DOING");
        }

        this.taskStatus = TaskStatus.DONE;
    }

}
