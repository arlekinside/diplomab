package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.RecurringExpense;
import com.github.arlekinside.diploma.data.repo.mf.RecurringExpenseRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/recurring/expenses")
public class RecurringExpenseController extends MoneyFlowController<RecurringExpense> {


    public RecurringExpenseController(RecurringExpenseRepo moneyFlowRepo) {
        super(moneyFlowRepo);
    }
    
}
