package com.github.arlekinside.diploma.scheduler;

import com.github.arlekinside.diploma.data.entity.SchedulerLog;
import com.github.arlekinside.diploma.data.enums.RecurringCycle;
import com.github.arlekinside.diploma.data.enums.SchedulerType;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.data.repo.SchedulerLogRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SavingsMonthlyJob implements Job {

    private final SavingRepo savingRepo;
    private final AccountingService accountingService;
    private final SchedulerLogRepo schedulerLogRepo;

    @Override
    @Scheduled(cron = "0 2 1 * * *") // Every first day of the month
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void run() {
        log.info("Running scheduled job");
        runInternal();
        log.info("Finished scheduled job");
    }

    public void runInternal() {
        var schedulerLog = new SchedulerLog();
        schedulerLog.setType(getType());
        try {
            var savings = savingRepo.findAllActive();
            var processed = accountingService.handleProcessSavings(savings);

            schedulerLog.setProcessedNum(processed);
            schedulerLog.setSuccess(true);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            schedulerLog.setProcessedNum(0);
            schedulerLog.setSuccess(false);
        }
        schedulerLogRepo.save(schedulerLog);
    }

    @Override
    public SchedulerType getType() {
        return SchedulerType.SAVING_MONTHLY;
    }
}
