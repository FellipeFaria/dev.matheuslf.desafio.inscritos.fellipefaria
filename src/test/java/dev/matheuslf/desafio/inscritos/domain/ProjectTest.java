package dev.matheuslf.desafio.inscritos.domain;

import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.domain.exceptions.DateException;
import dev.matheuslf.desafio.inscritos.domain.model.Project;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Name;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Title;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ProjectTest {

    @Test
    @DisplayName("Não deve permitir criar projeto com data final anterior à data inicial")
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusDays(1); // Data passada

        Assertions.assertThrows(DateException.class, () -> {
            new Project(
                    new Name("Projeto Inválido"),
                    new Description("Desc"),
                    new Date(start),
                    new Date(end)
            );
        });
    }

    @Test
    @DisplayName("Não deve permitir adicionar tarefa com prazo fora do período do projeto")
    void shouldThrowExceptionWhenTaskDateIsOutsideProjectDate() {
        // Arrange: Projeto de Hoje até +10 dias
        LocalDateTime projStart = LocalDateTime.now();
        LocalDateTime projEnd = projStart.plusDays(10);
        Project project = new Project(1L, new Name("Proj"), new Description("Desc"), new Date(projStart), new Date(projEnd));

        // Act & Assert 1: Tarefa antes do projeto começar
        Task taskBefore = createTaskWithDueDate(projStart.minusDays(1));
        Assertions.assertThrows(DateException.class, () -> project.addTask(taskBefore));

        // Act & Assert 2: Tarefa depois do projeto terminar
        Task taskAfter = createTaskWithDueDate(projEnd.plusDays(1));
        Assertions.assertThrows(DateException.class, () -> project.addTask(taskAfter));
    }

    // Método auxiliar para criar tarefa
    private Task createTaskWithDueDate(LocalDateTime dueDate) {
        return new Task(
                new Title("Task Teste"),
                new Description("Desc"),
                TaskPriority.MEDIUM,
                new Date(dueDate),
                1L
        );
    }
}