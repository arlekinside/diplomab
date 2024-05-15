package com.github.arlekinside.diploma.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.arlekinside.diploma.data.SecurityRoles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SUser")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User {

    @Builder
    public User(String username, String password, SecurityRoles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private String password;

    @Embedded
    @JsonIgnore
    private TimeData timeData;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SecurityRoles role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Saving> savings = new ArrayList<>();

    @Embedded
    @ToString.Exclude
    private MoneyData moneyData = new MoneyData();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
