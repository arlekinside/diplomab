package com.github.arlekinside.diploma.ws.dto;

import com.github.arlekinside.diploma.data.entity.Money;
import lombok.Data;

@Data
public class DashboardDTO {

    private Money budget = new Money();
    private Money monthIncome = new Money();
    private Money monthExpense = new Money();
    private Money hhRate = new Money();
    private Money savingsTotal = new Money();
    private Money savingsLeft = new Money();

    private MonthDataDTO lastMonth = new MonthDataDTO();
    private MonthDataDTO currentMonth = new MonthDataDTO();
    private MonthDataDTO nextMonth = new MonthDataDTO();

}
