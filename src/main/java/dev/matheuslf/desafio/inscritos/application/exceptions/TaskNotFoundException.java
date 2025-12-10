package dev.matheuslf.desafio.inscritos.application.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException() {
        super("Tarefa não encontrada");
    }
}
