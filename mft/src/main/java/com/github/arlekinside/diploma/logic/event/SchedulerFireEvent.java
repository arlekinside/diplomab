package com.github.arlekinside.diploma.logic.event;

import org.springframework.context.ApplicationEvent;

public class SchedulerFireEvent extends ApplicationEvent {

    public SchedulerFireEvent(Object source) {
        super(source);
    }

}
