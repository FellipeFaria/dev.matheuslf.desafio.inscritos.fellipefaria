package dev.matheuslf.desafio.inscritos.domain.exceptions;

public class DateException extends RuntimeException {
    public DateException(String message) {
        super(message);
    }

    public DateException() {
        super("Erro na data");
    }
}
