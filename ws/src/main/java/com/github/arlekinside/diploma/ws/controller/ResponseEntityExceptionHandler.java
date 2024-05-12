package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.logic.exception.ForbiddenException;
import com.github.arlekinside.diploma.ws.dto.NotFoundDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleUnAuthorizedException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
