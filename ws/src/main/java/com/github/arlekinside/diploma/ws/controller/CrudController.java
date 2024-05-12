package com.github.arlekinside.diploma.ws.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<T> {

    @PostMapping
    T create(Authentication auth, @RequestBody T t);

    @GetMapping
    List<T> readAll(Authentication auth);

    @GetMapping("/{id}")
    T read(Authentication auth, @PathVariable Long id);

    @PutMapping("/{id}")
    T update(Authentication auth, @PathVariable Long id, @RequestBody T t);

    @DeleteMapping("/{id}")
    void delete(Authentication auth, @PathVariable Long id);

}
