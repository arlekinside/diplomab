package com.github.arlekinside.diploma.ws.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<T> {

    @PostMapping
    T create(Authentication principal, @Valid @RequestBody T t);

    @GetMapping
    List<T> readAll(Authentication auth);

    @GetMapping("/{id}")
    T read(Authentication auth, @PathVariable Long id);

    @PutMapping("/{id}")
    @Transactional
    T update(Authentication auth, @PathVariable Long id, @RequestBody T t);

    @DeleteMapping("/{id}")
    @Transactional
    void delete(Authentication auth, @PathVariable Long id);

}
