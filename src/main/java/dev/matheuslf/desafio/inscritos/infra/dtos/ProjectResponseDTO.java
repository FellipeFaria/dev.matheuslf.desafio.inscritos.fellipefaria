package dev.matheuslf.desafio.inscritos.infra.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<TaskResponseDTO> tasks
) {
}
