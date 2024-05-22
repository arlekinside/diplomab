package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.SchedulerLog;
import com.github.arlekinside.diploma.data.repo.SchedulerLogRepo;
import com.github.arlekinside.diploma.logic.event.SchedulerFireEvent;
import com.github.arlekinside.diploma.ws.dto.SchedulerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController{

    private final ApplicationEventPublisher publisher;
    private final SchedulerLogRepo schedulerLogRepo;

    @PostMapping
    public void triggerJob(@RequestBody SchedulerDTO schedulerDTO) {
        publisher.publishEvent(new SchedulerFireEvent(schedulerDTO.getType()));
    }

    @GetMapping("/log")
    public List<SchedulerLog> getAllLogs() {
        return schedulerLogRepo.findAllAfterDate(LocalDateTime.now().minusMonths(1));
    }
}
