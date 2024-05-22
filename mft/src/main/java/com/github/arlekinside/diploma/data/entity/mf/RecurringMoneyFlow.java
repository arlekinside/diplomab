package com.github.arlekinside.diploma.data.entity.mf;

import com.github.arlekinside.diploma.data.enums.RecurringCycle;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RecurringMoneyFlow extends MoneyFlow {

    @Enumerated(EnumType.STRING)
    private RecurringCycle cycle;

}
