package com.github.arlekinside.diploma.scheduler;

import com.github.arlekinside.diploma.logic.event.SchedulerFireEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerFireEventListener implements ApplicationListener<SchedulerFireEvent> {

    private final List<Job> jobs;

    @Override
    public void onApplicationEvent(SchedulerFireEvent event) {
        var job = jobs.stream()
                .filter(j -> j.getType().equals(event.getSource()))
                .findFirst()
                .orElseThrow();
        log.info("Manually running job#{} START", job.getType());
        job.runInternal();
        log.info("Manually running job#{} DONE", job.getType());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return true;
    }
}
