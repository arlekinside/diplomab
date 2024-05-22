package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.RecurringExpense;
import com.github.arlekinside.diploma.data.repo.mf.MoneyFlowRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/recurring/expenses")
public class RecurringExpenseController extends MoneyFlowController<RecurringExpense> {


    public RecurringExpenseController(MoneyFlowRepo<RecurringExpense> moneyFlowRepo, AccountingService accountingService) {
        super(moneyFlowRepo, accountingService);
    }
}
