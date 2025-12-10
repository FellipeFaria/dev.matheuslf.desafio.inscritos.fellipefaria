package dev.matheuslf.desafio.inscritos.infra.dtos;

import dev.matheuslf.desafio.inscritos.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record TaskUpdateStatusDTO(
        @NotNull(message = "O campo de novo status é obrigatório")
        TaskStatus newStatus
) {
}
