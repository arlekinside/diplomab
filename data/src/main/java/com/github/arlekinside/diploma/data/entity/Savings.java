package com.github.arlekinside.diploma.data.entity;

import com.github.arlekinside.diploma.data.entity.finance.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        indexes = {
                @Index(columnList = "user_id")
        }
)
@Getter
@NoArgsConstructor
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Setter
    private Money money;

    @Embedded
    private TimeData timeData;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "saving_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            indexes = @Index(columnList = "user_id")
    )
    private List<User> members;
}
