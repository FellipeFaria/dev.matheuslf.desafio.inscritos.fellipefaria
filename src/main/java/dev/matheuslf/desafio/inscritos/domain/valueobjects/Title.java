package dev.matheuslf.desafio.inscritos.domain.valueobjects;

import dev.matheuslf.desafio.inscritos.domain.exceptions.LargerThanMaximumLength;
import dev.matheuslf.desafio.inscritos.domain.exceptions.SmallerThanMinimumLength;

public record Title(String value) {
    public Title {
        final int MINIMUM_LENGTH = 5;
        final int MAXIMUM_LENGTH = 150;

        if (value.length() < MINIMUM_LENGTH) {
            throw new SmallerThanMinimumLength("Título", MINIMUM_LENGTH);
        }

        if (value.length() > MAXIMUM_LENGTH) {
            throw new LargerThanMaximumLength("Título", MAXIMUM_LENGTH);
        }
    }
}
