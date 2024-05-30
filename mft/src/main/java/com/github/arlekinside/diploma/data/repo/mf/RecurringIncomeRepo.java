package com.github.arlekinside.diploma.data.repo.mf;

import com.github.arlekinside.diploma.data.entity.mf.RecurringIncome;
import com.github.arlekinside.diploma.data.enums.RecurringCycle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringIncomeRepo extends MoneyFlowRepo<RecurringIncome> {

    List<RecurringIncome> findAllByCycle(RecurringCycle cycle);

}
