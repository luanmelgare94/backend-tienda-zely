package com.tienda.zely.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), request, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(
            ConflictException ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), request, HttpStatus.CONFLICT),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Error de validacion en los datos enviados")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(violation -> fieldErrors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Error de validacion en los parametros enviados")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        return new ResponseEntity<>(
                buildErrorResponse("Ocurrio un error interno en el servidor", request, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildErrorResponse(String message, HttpServletRequest request, HttpStatus status) {
        return ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
