package com.github.arlekinside.diploma.data.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

@Embeddable
@Data
@AllArgsConstructor
public final class Money {

    public Money() {
        this(0, "USD");
    }

    public Money(long amount) {
        this(amount, "USD");
    }

    public Money(long amount, String currency) {
        this.amount = amount;
        this.currency = Currency.getInstance(currency);
    }

    @Min(value = 1, message = "Amount cannot be lower than 0")
    private long amount;

    @NotNull(message = "Currency cannot be empty")
    private Currency currency;

    public Money increment(long amount) {
        this.amount += amount;
        return this;
    }

    public Money decremennt(long amount) {
        this.amount -= amount;
        return this;
    }

}
