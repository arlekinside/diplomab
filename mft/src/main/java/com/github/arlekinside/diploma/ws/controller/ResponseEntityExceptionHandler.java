package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.logic.exception.ForbiddenException;
import com.github.arlekinside.diploma.ws.dto.ErrorDTO;
import com.github.arlekinside.diploma.ws.dto.NotFoundDTO;
import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundDTO> handleNotFoundException(NotFoundException ex) {
        var id = ex.getId();
        var clazz = ex.getClazz();
        var builder = NotFoundDTO.builder();

        if (id != null) {
            builder.id(id);
        }
        if (clazz != null) {
            builder.type(clazz.getSimpleName());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(builder.build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        StringBuilder message = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            message.append(fieldName).append(" - ").append(errorMessage).append('\n');
        });

        var res = new ErrorDTO();
        res.setMessage(message.toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(res);
    }
}
