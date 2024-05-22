package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.entity.Budget;
import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import com.github.arlekinside.diploma.data.entity.mf.RecurringMoneyFlow;
import com.github.arlekinside.diploma.data.repo.BudgetRepo;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.logic.exception.BadRequestException;
import com.github.arlekinside.diploma.ws.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountingServiceImpl implements AccountingService {

    private final BudgetRepo budgetRepo;
    private final SavingRepo savingRepo;
    private final UserService userService;


    @Override
    public DashboardDTO analyzeUserData(User user) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleMoneyFlowCreated(MoneyFlow moneyFlow) {
        var mfAmount = moneyFlow.getMoney().getAmount();
        if (moneyFlow.isExpense()) {
            mfAmount *= -1;
        }

        updateBudget(mfAmount, moneyFlow.getUser().getBudget(), moneyFlow.getUser());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleMoneyFlowDeleted(MoneyFlow moneyFlow) {
        var mfAmount = moneyFlow.getMoney().getAmount();
        if (!moneyFlow.isExpense()) {
            mfAmount *= -1;
        }

        updateBudget(mfAmount, moneyFlow.getUser().getBudget(), moneyFlow.getUser());
    }

    @Override
    public long handleProcessMoneyFlow(List<? extends MoneyFlow> moneyFlowList) {
        moneyFlowList.forEach(this::handleMoneyFlowCreated);
        return moneyFlowList.size();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleSavingDeposit(Saving saving) {
        if (saving.getFinished() == Boolean.TRUE) {
            log.info("Saving#{} is finished, skipping...", saving.getId());
            return;
        }
        long depositAmount;

        var percent = saving.getMonthlyPercent();
        var user = saving.getUser();
        var budget = user.getBudget();

        var budgetAmount = budget.getMoney().getAmount();
        depositAmount = Math.min(
                budget.getMoney().getAmount() * (percent / 100), // Calculated amount
                saving.getTarget().getAmount() - saving.getMoney().getAmount() // Amount left
        );

        if (budgetAmount < depositAmount) {
            log.info("Couldn't take money from budget#{} for saving#{} as there's not enough money", budget.getId(), saving.getId());
            return;
        }

        saving.getMoney().increment(depositAmount);

        saving.setUser(user);

        if (saving.getMoney().getAmount() == saving.getTarget().getAmount()) {
            saving.setFinished(true);
        }

        updateBudget(-depositAmount, budget, user);
        savingRepo.save(saving);
    }

    @Override
    public void handleSavingCreated(Saving saving) {
        var user = saving.getUser();
        var budget = user.getBudget();

        var budgetAmount = budget.getMoney().getAmount();
        var depositAmount = saving.getMoney().getAmount();

        if (budgetAmount < depositAmount) {
            log.info("Couldn't take money from budget#{} for saving#{} as there's not enough money", budget.getId(), saving.getId());
            throw new BadRequestException("Budget too low for initial amount");
        }

        var totalPercent = saving.getMonthlyPercent() + savingRepo.findAllActiveByUser(user).stream().mapToInt(Saving::getMonthlyPercent).sum();
        if (totalPercent > 100) {
            log.info("Total percent for saving#{} is greater than 100", saving.getId());
            throw new BadRequestException("Total monthly percent of all savings cannot exceed 100%");
        }

        saving.setUser(user);

        if (saving.getMoney().getAmount() == saving.getTarget().getAmount()) {
            saving.setFinished(true);
        }

        updateBudget(-depositAmount, budget, user);
        savingRepo.save(saving);
    }

    @Override
    public long handleProcessSavings(List<Saving> savingList) {
        savingList.forEach(this::handleSavingDeposit);
        return savingList.size();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleSavingDeleted(Saving saving) {
        var backAmount = saving.getTarget().getAmount();
        var user = saving.getUser();
        var budget = user.getBudget();

        updateBudget(backAmount, budget, user);
    }

    @Override
    public DashboardDTO buildDashboard(Long userId) {
        var res = new DashboardDTO();
        var user = userService.read(userId);
        var moneyData = user.getMoneyData();

        //Budget
        res.setBudget(user.getBudget().getMoney());

        //Current month
        res.getMonthIncome()
                .increment(calculateOneTimeMFMonthSum(moneyData.getIncomes(), YearMonth.now()))
                .increment(calculateRecurringMFMonthSum(moneyData.getRecurringIncomes(), YearMonth.now()));

        res.getMonthExpense()
                .increment(calculateOneTimeMFMonthSum(moneyData.getExpenses(), YearMonth.now()))
                .increment(calculateRecurringMFMonthSum(moneyData.getRecurringExpenses(), YearMonth.now()));

        res.getHhRate()
                .increment(res.getMonthIncome().getAmount() / YearMonth.now().lengthOfMonth());

        //Current month
        res.getCurrentMonth().setIncomes(res.getMonthIncome());

        res.getCurrentMonth().setExpenses(res.getMonthExpense());

        res.getCurrentMonth().setHh(res.getHhRate());

        //Last month
        res.getLastMonth().getIncomes()
                .increment(calculateOneTimeMFMonthSum(moneyData.getIncomes(), YearMonth.now().minusMonths(1)))
                .increment(calculateRecurringMFMonthSum(moneyData.getRecurringIncomes(), YearMonth.now().minusMonths(1)));

        res.getLastMonth().getExpenses()
                .increment(calculateOneTimeMFMonthSum(moneyData.getExpenses(), YearMonth.now().minusMonths(1)))
                .increment(calculateRecurringMFMonthSum(moneyData.getRecurringExpenses(), YearMonth.now().minusMonths(1)));

        res.getLastMonth().getHh()
                .increment(res.getLastMonth().getIncomes().getAmount() / YearMonth.now().lengthOfMonth());

        //Next month
        res.getNextMonth().getIncomes()
                .increment((res.getLastMonth().getIncomes().getAmount() + res.getCurrentMonth().getIncomes().getAmount() * 2) / 3);

        res.getNextMonth().getExpenses()
                .increment((res.getLastMonth().getExpenses().getAmount() + res.getCurrentMonth().getExpenses().getAmount() * 2) / 3);

        res.getNextMonth().getHh()
                .increment((res.getLastMonth().getHh().getAmount() + res.getCurrentMonth().getHh().getAmount() * 2) / 3);

        //Savings
        res.getSavingsTotal()
                .increment(
                        user.getSavings().stream()
                                .filter(s -> s.getFinished() != Boolean.TRUE)
                                .mapToLong(s -> s.getMoney().getAmount())
                                .sum()
                );

        res.getSavingsLeft()
                .increment(user.getSavings().stream()
                        .filter(s -> s.getFinished() != Boolean.TRUE)
                        .mapToLong(s -> s.getTarget().getAmount())
                        .sum())
                .decremennt(res.getSavingsTotal().getAmount());

        return res;
    }

    private <T extends MoneyFlow> long calculateOneTimeMFMonthSum(List<T> moneyFlowList, YearMonth yearMonth) {
        return moneyFlowList.stream()
                .filter(mf -> isInMonth(mf.getTimeData().getDateCreated(), yearMonth))
                .mapToLong(mf -> mf.getMoney().getAmount())
                .sum();
    }

    private <T extends RecurringMoneyFlow> long calculateRecurringMFMonthSum(List<T> moneyFlowList, YearMonth yearMonth) {
        var monthDays = yearMonth.lengthOfMonth();
        return moneyFlowList.stream()
                .mapToLong(mf -> switch (mf.getCycle()) {
                    case DAILY -> mf.getMoney().getAmount() * monthDays;
                    case MONTHLY -> mf.getMoney().getAmount();
                }).sum();

    }

    private static boolean isInMonth(LocalDateTime dateTime, YearMonth yearMonth) {
        return !dateTime.toLocalDate().isBefore(yearMonth.atDay(1)) &&
                !dateTime.toLocalDate().isAfter(yearMonth.atEndOfMonth());
    }

    private void updateBudget(Long amount, Budget budget, User user) {
        budget.setUser(user);
        budget.getMoney().increment(amount);

        budgetRepo.save(budget);
    }
}
