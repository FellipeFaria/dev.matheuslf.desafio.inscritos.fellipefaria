package dev.matheuslf.desafio.inscritos.domain.model.filter;

import dev.matheuslf.desafio.inscritos.domain.enums.TaskPriority;
import dev.matheuslf.desafio.inscritos.domain.enums.TaskStatus;

public record TaskFilter(
        Long projectId,
        TaskStatus taskStatus,
        TaskPriority taskPriority
) {
}
