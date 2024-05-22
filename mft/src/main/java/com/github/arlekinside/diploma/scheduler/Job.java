package com.github.arlekinside.diploma.scheduler;

import com.github.arlekinside.diploma.data.enums.SchedulerType;

public interface Job {

    void run();

    void runInternal();

    SchedulerType getType();
}
