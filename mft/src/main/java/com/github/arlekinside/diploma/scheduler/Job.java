package com.github.arlekinside.diploma.scheduler;

import com.github.arlekinside.diploma.data.SchedulerType;

public interface Job {

    void run();

    void runInternal();

    SchedulerType getType();
}
