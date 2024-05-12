package com.github.arlekinside.diploma.data.entity;

import com.github.arlekinside.diploma.data.entity.finance.Money;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(indexes = {
        @Index(columnList = "user_id")
})
@Getter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Money price;

    private byte incomePercent;

    private byte recurringIncomePercent;

    @Embedded
    private TimeData timeData;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}