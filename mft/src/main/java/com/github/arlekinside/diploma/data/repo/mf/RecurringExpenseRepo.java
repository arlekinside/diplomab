package com.github.arlekinside.diploma.data.repo.mf;

import com.github.arlekinside.diploma.data.enums.RecurringCycle;
import com.github.arlekinside.diploma.data.entity.mf.RecurringExpense;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringExpenseRepo extends MoneyFlowRepo<RecurringExpense> {

    List<RecurringExpense> findAllByCycle(RecurringCycle cycle);
}
