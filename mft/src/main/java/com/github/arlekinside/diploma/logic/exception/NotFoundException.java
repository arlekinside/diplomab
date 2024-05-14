package com.github.arlekinside.diploma.logic.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class NotFoundException extends RuntimeException {

    private final Long id;

    private final Class<?> clazz;

}
