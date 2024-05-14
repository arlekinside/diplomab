package com.github.arlekinside.diploma.data.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Table(indexes = {
        @Index(columnList = "user_id")
})
@Data
public class Goal implements UserAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Money price;

    private byte incomePercent;

    private byte recurringIncomePercent;

    @Embedded
    @JsonIgnore
    private TimeData timeData;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
