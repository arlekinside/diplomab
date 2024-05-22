package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.Income;
import com.github.arlekinside.diploma.data.repo.mf.MoneyFlowRepo;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mf/incomes")
public class IncomeController extends MoneyFlowController<Income> {


    public IncomeController(MoneyFlowRepo<Income> moneyFlowRepo, AccountingService accountingService) {
        super(moneyFlowRepo, accountingService);
    }

    @Override
    public Income create(Authentication principal, Income income) {
        var moneyFlow = super.create(principal, income);
        accountingService.handleMoneyFlowCreated(moneyFlow);
        return moneyFlow;
    }

    @Override
    public void delete(Authentication auth, Long id) {
        accountingService.handleMoneyFlowDeleted(read(auth, id));
        super.delete(auth, id);
    }
}
