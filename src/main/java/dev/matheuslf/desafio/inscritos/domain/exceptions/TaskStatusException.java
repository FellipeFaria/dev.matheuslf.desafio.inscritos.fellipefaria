package dev.matheuslf.desafio.inscritos.domain.exceptions;

public class TaskStatusException extends RuntimeException {
    public TaskStatusException(String message) {
        super(message);
    }

    public TaskStatusException() {
        super("Erro no TaskStatus");
    }
}
