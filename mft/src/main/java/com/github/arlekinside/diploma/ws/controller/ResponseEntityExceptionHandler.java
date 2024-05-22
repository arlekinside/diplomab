package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.logic.exception.BadRequestException;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.ws.dto.ErrorDTO;
import com.github.arlekinside.diploma.ws.dto.NotFoundDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
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
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {

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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException ex) {

        var res = new ErrorDTO();
        res.setMessage(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(res);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(Throwable t) {

        var res = new ErrorDTO();
        res.setMessage("Internal service error");

        log.error("Caught an error", t);

        return ResponseEntity.internalServerError().build();
    }
}
