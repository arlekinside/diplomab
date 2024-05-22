package com.github.arlekinside.diploma.scheduler;

import com.github.arlekinside.diploma.data.RecurringCycle;
import com.github.arlekinside.diploma.data.SchedulerType;
import com.github.arlekinside.diploma.data.repo.mf.RecurringExpenseRepo;
import com.github.arlekinside.diploma.data.repo.mf.RecurringIncomeRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MoneyFlowDailyJob implements Job {

    private final RecurringIncomeRepo incomeRepo;
    private final RecurringExpenseRepo expenseRepo;
    private final AccountingService accountingService;

    @Override
    @Scheduled(cron = "0 2 * * * *") //Every day 2am
    public void run() {
        log.info("Running scheduled job");
        runInternal();
        log.info("Finished scheduled job");
    }

    public void runInternal() {
        var incomes = incomeRepo.findAllByCycle(RecurringCycle.DAILY);
        accountingService.handleProcessMoneyFlow(incomes);
        var expenses = expenseRepo.findAllByCycle(RecurringCycle.DAILY);
        accountingService.handleProcessMoneyFlow(expenses);
    }

    @Override
    public SchedulerType getType() {
        return SchedulerType.MF_DAILY;
    }
}
