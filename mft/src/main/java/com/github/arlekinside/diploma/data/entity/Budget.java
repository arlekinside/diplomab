package com.github.arlekinside.diploma.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(unique = true)
    private User user;

    @Embedded
    private Money money = new Money();

    @Embedded
    private TimeData timeData;

}
