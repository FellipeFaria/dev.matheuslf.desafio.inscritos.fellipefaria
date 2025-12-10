package dev.matheuslf.desafio.inscritos.infra.exception;

import dev.matheuslf.desafio.inscritos.application.exceptions.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.application.exceptions.TaskNotFoundException;
import dev.matheuslf.desafio.inscritos.domain.exceptions.*;
import dev.matheuslf.desafio.inscritos.infra.dtos.StandardErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ProjectNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<StandardErrorDTO> handleNotFound(RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Resource Not Found",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({
            LargerThanMaximumLength.class,
            SmallerThanMinimumLength.class,
            DateException.class,
            TaskStatusException.class,
            ProjectExpiredException.class
    })
    public ResponseEntity<StandardErrorDTO> handleBusinessRuleException(RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Business Rule Violation",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Validation Error",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorDTO> handleBadRequest(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Bad Request",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleGeneral(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Internal Server Error",
                "Ocorreu um erro inesperado. Contate o suporte.",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }
}
