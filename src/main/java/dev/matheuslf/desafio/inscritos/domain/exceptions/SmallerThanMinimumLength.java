package dev.matheuslf.desafio.inscritos.domain.exceptions;

public class SmallerThanMinimumLength extends RuntimeException {
    public SmallerThanMinimumLength(String value, int length) {
        super("O campo " + value + " deve conter no mínimo " + length + " caracteres");
    }

    public SmallerThanMinimumLength() {
        super("O valor informado não possui o tamanho mínimo de digitos.");
    }
}
