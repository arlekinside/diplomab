package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.entity.Budget;
import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import com.github.arlekinside.diploma.data.repo.BudgetRepo;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.logic.exception.BadRequestException;
import com.github.arlekinside.diploma.ws.dto.DashboardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountingServiceImpl implements AccountingService {

    private final BudgetRepo budgetRepo;
    private final SavingRepo savingRepo;

    public AccountingServiceImpl(BudgetRepo budgetRepo, SavingRepo savingRepo, SavingRepo savingRepo1) {
        this.budgetRepo = budgetRepo;
        this.savingRepo = savingRepo1;
    }

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
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleSavingDeposit(Saving saving, boolean create) {
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleSavingDeleted(Saving saving) {
        var backAmount = saving.getTarget().getAmount();
        var user = saving.getUser();
        var budget = user.getBudget();

        updateBudget(backAmount, budget, user);
    }


    private void updateBudget(Long amount, Budget budget, User user) {
        budget.setUser(user);
        budget.getMoney().increment(amount);

        budgetRepo.save(budget);
    }
}
