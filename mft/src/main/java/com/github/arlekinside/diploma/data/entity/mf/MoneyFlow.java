package com.github.arlekinside.diploma.data.entity.mf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.arlekinside.diploma.data.entity.Money;
import com.github.arlekinside.diploma.data.entity.TimeData;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.UserAware;
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
public abstract class MoneyFlow implements UserAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money money;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Embedded
    @JsonIgnore
    private TimeData timeData;
}
