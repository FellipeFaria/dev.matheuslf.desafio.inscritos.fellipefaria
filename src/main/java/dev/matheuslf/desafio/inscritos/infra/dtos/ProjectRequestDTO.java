package dev.matheuslf.desafio.inscritos.infra.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ProjectRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "A data de inicio é obrigatória") LocalDateTime startDate,
        LocalDateTime endDate
) {
}
