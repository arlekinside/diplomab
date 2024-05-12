package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.Expense;
import com.github.arlekinside.diploma.data.repo.mf.ExpenseRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/expense")
public class ExpenseController extends MoneyFlowController<Expense> {


    public ExpenseController(ExpenseRepo moneyFlowRepo) {
        super(moneyFlowRepo);
    }

}
