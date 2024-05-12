package com.github.arlekinside.diploma.data.entity.finance;

import com.github.arlekinside.diploma.data.RecurringCycle;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RecurringMoneyFlow extends MoneyFlow {

    @Enumerated(EnumType.STRING)
    private RecurringCycle cycle;

}
