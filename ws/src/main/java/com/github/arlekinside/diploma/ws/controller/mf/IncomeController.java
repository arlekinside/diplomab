package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.Income;
import com.github.arlekinside.diploma.data.repo.MoneyFlowRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/income")
public class IncomeController extends MoneyFlowController<Income> {

    public IncomeController(MoneyFlowRepo<Income> moneyFlowRepo) {
        super(moneyFlowRepo);
    }
}
