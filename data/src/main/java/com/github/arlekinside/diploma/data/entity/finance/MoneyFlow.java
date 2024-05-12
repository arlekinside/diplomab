package com.github.arlekinside.diploma.data.entity.finance;

import com.github.arlekinside.diploma.data.entity.TimeData;
import com.github.arlekinside.diploma.data.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(indexes = {
        @Index(columnList = "user_id, type"),
        @Index(columnList = "date_created")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class MoneyFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money money;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private TimeData timeData;
}
