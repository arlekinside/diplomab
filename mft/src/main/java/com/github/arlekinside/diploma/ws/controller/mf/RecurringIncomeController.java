package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.RecurringIncome;
import com.github.arlekinside.diploma.data.repo.mf.MoneyFlowRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/recurring/incomes")
public class RecurringIncomeController extends MoneyFlowController<RecurringIncome> {


    public RecurringIncomeController(MoneyFlowRepo<RecurringIncome> moneyFlowRepo, AccountingService accountingService) {
        super(moneyFlowRepo, accountingService);
    }
}
