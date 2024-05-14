package com.github.arlekinside.diploma.data.entity;

import com.github.arlekinside.diploma.data.entity.mf.Expense;
import com.github.arlekinside.diploma.data.entity.mf.Income;
import com.github.arlekinside.diploma.data.entity.mf.RecurringExpense;
import com.github.arlekinside.diploma.data.entity.mf.RecurringIncome;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class MoneyData {

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RecurringIncome> recurringIncomes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RecurringExpense> recurringExpenses = new ArrayList<>();

}
