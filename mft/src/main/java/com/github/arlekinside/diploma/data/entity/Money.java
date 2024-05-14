package com.github.arlekinside.diploma.data.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public final class Money {

    public Money(long amount) {
        this(amount, "USD");
    }

    public Money(long amount, String currency) {
        this.amount = amount;
        this.currency = Currency.getInstance(currency);
    }

    private long amount;

    private Currency currency;

}
