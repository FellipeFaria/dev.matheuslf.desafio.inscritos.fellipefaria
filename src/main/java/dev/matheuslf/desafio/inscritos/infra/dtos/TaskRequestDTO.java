package dev.matheuslf.desafio.inscritos.infra.dtos;

import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String title,

        String description,

        @NotNull(message = "O campo de prioridade da tárefa é obrigatório")
        TaskPriority priority,

        @NotNull(message = "O prazo da tárefa é obrigatório")
        LocalDateTime dueDate,

        @NotNull(message = "O id do projeto é obrigatório")
        Long projectId
) {
}
