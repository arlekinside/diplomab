package com.github.arlekinside.diploma.scheduler;

import com.github.arlekinside.diploma.data.SchedulerType;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SavingsMonthlyJob implements Job {

    private final SavingRepo savingRepo;
    private final AccountingService accountingService;

    @Override
    @Scheduled(cron = "0 2 1 * * *") // Every first day of the month
    public void run() {
        log.info("Running scheduled job");
        runInternal();
        log.info("Finished scheduled job");
    }

    public void runInternal() {
        var savings = savingRepo.findAllActive();
        accountingService.handleProcessSavings(savings);
    }

    @Override
    public SchedulerType getType() {
        return SchedulerType.SAVING_MONTHLY;
    }
}
