package com.github.arlekinside.diploma.data.repo.mf;

import com.github.arlekinside.diploma.data.entity.mf.Expense;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepo extends MoneyFlowRepo<Expense> {

}
