package com.github.arlekinside.diploma.data.entity.mf;

import jakarta.persistence.Entity;

@Entity
public class RecurringIncome extends RecurringMoneyFlow {

    @Override
    public boolean isExpense() {
        return false;
    }
}
