package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.logic.service.SystemJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class SystemJobController {

    private SystemJobService systemJobService;

    @PutMapping("/{id}")
    public void retryJob(@PathVariable Long id) {

    }
}
