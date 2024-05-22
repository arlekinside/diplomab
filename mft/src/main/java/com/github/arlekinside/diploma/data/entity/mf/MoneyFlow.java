package com.github.arlekinside.diploma.data.entity.mf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.arlekinside.diploma.data.entity.Money;
import com.github.arlekinside.diploma.data.entity.TimeData;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.UserAware;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.format.DateTimeFormatter;

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

    @Pattern(regexp = "[0-9\\w]+", message = "Name should contain only letters and numbers")
    private String name;

    @Embedded
    @Valid
    @NotNull(message = "Money is obligatory")
    private Money money;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Embedded
    @JsonIgnore
    private TimeData timeData;

    @Transient
    private String dateCreated;

    @PostLoad
    private void postLoad() {
        this.dateCreated = timeData.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd"));
    }

    public abstract boolean isExpense();

}
