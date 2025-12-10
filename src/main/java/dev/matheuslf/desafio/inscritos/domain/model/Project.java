package dev.matheuslf.desafio.inscritos.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.matheuslf.desafio.inscritos.domain.exceptions.DateException;
import dev.matheuslf.desafio.inscritos.domain.exceptions.ProjectExpiredException;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Name;
import lombok.Getter;

@Getter
public class Project {
    private Long id;
    private final Name name;
    private final Description description;
    private final Date startDate;
    private final Date endDate;
    private final List<Task> tasks = new ArrayList<>();

    public Project (Name name, Description description, Date startDate, Date endDate) {
        if (endDate != null) {
            if (endDate.isBeforeThan(startDate)) {
                throw new DateException("A data de fim de projeto não pode ser anterior ao de inicio.");
            }
        }

        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project (Long id, Name name, Description description, Date startDate, Date endDate) {
        this(name, description, startDate, endDate);
        this.id = id;
    }

    public void addTask(Task task) {
        if (task.getDueDate().isBeforeThan(this.startDate)) {
            throw new DateException("A data de fim da tarefa não pode ser anterior a data de inicio do projeto");
        }

        if (this.endDate.value() != null) {
            if (task.getDueDate().isAfterThan(this.endDate)) {
                throw new DateException("A data de fim da terefa não pode ser depois da data de fim do projeto");
            }

            if (this.endDate.value().isBefore(LocalDateTime.now())) {
                throw new ProjectExpiredException("Projeto expirado!");
            }
        }

        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.removeIf(t -> t.getId().equals(task.getId()));
    }
}
