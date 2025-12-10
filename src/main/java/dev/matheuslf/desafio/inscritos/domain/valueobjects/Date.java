package dev.matheuslf.desafio.inscritos.domain.valueobjects;

import java.time.LocalDateTime;

public record Date(LocalDateTime value) {
    public boolean isBeforeThan(Date date) {
        return value != null && value.isBefore(date.value);
    }

    public boolean isAfterThan(Date date) {
        return value != null && value.isAfter(date.value);
    }
}
