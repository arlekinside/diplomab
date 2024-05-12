package com.github.arlekinside.diploma.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    private String name;

    @Setter
    private Money money;

    @Embedded
    @JsonIgnore
    private TimeData timeData;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "saving_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            indexes = @Index(columnList = "user_id")
    )
    @JsonIgnore
    private List<User> members;
}
