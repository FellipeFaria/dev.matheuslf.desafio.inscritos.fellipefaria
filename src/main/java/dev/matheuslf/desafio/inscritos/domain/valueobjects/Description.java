package dev.matheuslf.desafio.inscritos.domain.valueobjects;

import dev.matheuslf.desafio.inscritos.domain.exceptions.LargerThanMaximumLength;

public record Description(String value) {
    public Description {
        final int MAXIMUM_LENGTH = 4000;

        if (value != null && value.length() > MAXIMUM_LENGTH) {
            throw new LargerThanMaximumLength("Descrição", MAXIMUM_LENGTH);
        }
    }
}
