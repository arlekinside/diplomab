package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.logic.event.SchedulerFireEvent;
import com.github.arlekinside.diploma.ws.dto.SchedulerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController{

    private final ApplicationEventPublisher publisher;

    @PostMapping
    public void triggerJob(@RequestBody SchedulerDTO schedulerDTO) {
        publisher.publishEvent(new SchedulerFireEvent(schedulerDTO.getType()));
    }
}
