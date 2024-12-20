package com.confetaria.confetaria_backend.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(
                "Recurso não encontrado",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse errorMessage = new ErrorResponse(
                "Violação de restrições",
                HttpStatus.CONFLICT.value(),
                ex.getRootCause().getMessage()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse(
                "Erro interno",
                statusCode.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorMessage, headers, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ValidationObject> errors = getErrors(ex);
        ErrorValidation errorValidation = getErrorValidation(ex, status, errors);
        return new ResponseEntity<>(errorValidation, headers, status);

    }

    private ErrorValidation getErrorValidation(MethodArgumentNotValidException ex, HttpStatusCode status, List<ValidationObject> errors) {
        return ErrorValidation.errorValidationBuilder()
                .title("Requisição possui campos inválidos")
                .status(status.value())
                .detail(ex.getMessage())
                .erros(errors)
                .build();
    }

    private List<ValidationObject> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ValidationObject (
                        error.getDefaultMessage(),
                        error.getField(),
                        error.getRejectedValue()
                    ))
                .collect(Collectors.toList());
    }
}
