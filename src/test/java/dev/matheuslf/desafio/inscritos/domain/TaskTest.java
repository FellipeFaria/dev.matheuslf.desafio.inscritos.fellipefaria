package dev.matheuslf.desafio.inscritos.domain;

import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskStatus;
import dev.matheuslf.desafio.inscritos.domain.exceptions.TaskStatusException;
import dev.matheuslf.desafio.inscritos.domain.model.Task;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Date;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Description;
import dev.matheuslf.desafio.inscritos.domain.valueobjects.Title;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TaskTest {

    @Test
    @DisplayName("Deve iniciar uma tarefa (TODO -> DOING) com sucesso")
    void shouldStartTaskSuccessfully() {
        // Arrange
        Task task = new Task(
                new Title("Test Task"),
                new Description("Desc"),
                TaskPriority.LOW,
                new Date(LocalDateTime.now().plusDays(1)),
                1L
        );

        // Act
        task.doTask(); //

        // Assert
        Assertions.assertEquals(TaskStatus.DOING, task.getTaskStatus());
    }

    @Test
    @DisplayName("Não deve permitir concluir uma tarefa que ainda está em TODO")
    void shouldThrowExceptionWhenFinishingTodoTask() {
        Task task = new Task(
                new Title("Test Task"),
                new Description("Desc"),
                TaskPriority.LOW,
                new Date(LocalDateTime.now().plusDays(1)),
                1L
        );

        // Act & Assert
        // Tentar pular de TODO direto para DONE deve lançar erro
        Assertions.assertThrows(TaskStatusException.class, task::doneTask);
    }

    @Test
    @DisplayName("Deve concluir uma tarefa (DOING -> DONE) com sucesso")
    void shouldFinishTaskSuccessfully() {
        Task task = new Task(
                new Title("Test Task"),
                new Description("Desc"),
                TaskPriority.LOW,
                new Date(LocalDateTime.now().plusDays(1)),
                1L
        );
        task.doTask(); // Muda para DOING

        // Act
        task.doneTask();

        // Assert
        Assertions.assertEquals(TaskStatus.DONE, task.getTaskStatus());
    }
}