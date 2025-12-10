package dev.matheuslf.desafio.inscritos.domain.valueobjects;

import dev.matheuslf.desafio.inscritos.domain.exceptions.LargerThanMaximumLength;
import dev.matheuslf.desafio.inscritos.domain.exceptions.SmallerThanMinimumLength;

public record Name(String value) {
    public Name {
        final int MINIMUM_LENGTH = 3;
        final int MAXIMUM_LENGTH = 100;

        if (value.length() < MINIMUM_LENGTH) {
            throw new SmallerThanMinimumLength("Nome", MINIMUM_LENGTH);
        }

        if (value.length() > MAXIMUM_LENGTH) {
            throw new LargerThanMaximumLength("Nome", MAXIMUM_LENGTH);
        }
    }
}
