package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.Expense;
import com.github.arlekinside.diploma.data.repo.mf.MoneyFlowRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/expenses")
public class ExpenseController extends MoneyFlowController<Expense> {

    public ExpenseController(MoneyFlowRepo<Expense> moneyFlowRepo, AccountingService accountingService) {
        super(moneyFlowRepo, accountingService);
    }

    @Override
    public Expense create(Authentication principal, Expense expense) {
        var moneyFlow = super.create(principal, expense);
        accountingService.handleMoneyFlowCreated(moneyFlow);
        return moneyFlow;
    }

    @Override
    public void delete(Authentication auth, Long id) {
        accountingService.handleMoneyFlowDeleted(read(auth, id));
        super.delete(auth, id);
    }
}
