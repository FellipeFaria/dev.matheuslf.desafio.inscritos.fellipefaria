package dev.matheuslf.desafio.inscritos.domain.exceptions;

public class ProjectExpiredException extends RuntimeException {
    public ProjectExpiredException(String message) {
        super(message);
    }

    public ProjectExpiredException() {
        super("O projeto expirou");
    }
}
