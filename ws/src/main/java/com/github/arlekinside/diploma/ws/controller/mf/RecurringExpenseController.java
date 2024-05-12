package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.RecurringExpense;
import com.github.arlekinside.diploma.data.repo.MoneyFlowRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/recurring/expense")
public class RecurringExpenseController extends MoneyFlowController<RecurringExpense> {


    public RecurringExpenseController(MoneyFlowRepo<RecurringExpense> moneyFlowRepo) {
        super(moneyFlowRepo);
    }
    
}
