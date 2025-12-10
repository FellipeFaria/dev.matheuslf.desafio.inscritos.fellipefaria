package dev.matheuslf.desafio.inscritos.domain.exceptions;

public class LargerThanMaximumLength extends RuntimeException {
    public LargerThanMaximumLength(String value, int length) {
        super("O campo " + value + " deve conter no máximo " + length + " caracteres");
    }

    public LargerThanMaximumLength() {
        super("A String ultrapassou o tamanho máximo permitido");
    }
}
