package com.github.arlekinside.diploma.data.entity.mf;

import jakarta.persistence.Entity;

@Entity
public class RecurringExpense extends RecurringMoneyFlow {

    @Override
    public boolean isExpense() {
        return true;
    }
}
