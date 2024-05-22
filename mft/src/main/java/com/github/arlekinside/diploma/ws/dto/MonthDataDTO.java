package com.github.arlekinside.diploma.ws.dto;

import com.github.arlekinside.diploma.data.entity.Money;
import lombok.Data;

@Data
public class MonthDataDTO {
    private Money incomes = new Money();
    private Money expenses = new Money();
    private Money hh = new Money();
}
