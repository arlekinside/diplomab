package com.github.arlekinside.diploma.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Entity
@Table(
        indexes = {
                @Index(columnList = "user_id")
        }
)
@Data
@NoArgsConstructor
public class Saving implements UserAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[0-9\\w]+", message = "Name should contain only letters and numbers")
    private String name;

    @Embedded
    private Money money = new Money();

    @Valid
    @NotNull(message = "Target is obligatory")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "target_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "target_currency"))
    })
    private Money target = new Money();

    @Min(value = 1, message = "Percents should be between 1 and 100")
    @Max(value = 100, message = "Percents should be between 1 and 100")
    private int monthlyPercent;

    @Embedded
    @JsonIgnore
    private TimeData timeData;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Transient
    private String dateCreated;

    private Boolean finished;

    @PostLoad
    private void postLoad() {
        this.dateCreated = timeData.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd"));
    }
}
