package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.Income;
import com.github.arlekinside.diploma.data.repo.mf.IncomeRepo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mf/incomes")
public class IncomeController extends MoneyFlowController<Income> {

    public IncomeController(IncomeRepo moneyFlowRepo) {
        super(moneyFlowRepo);
    }

}
