package com.github.arlekinside.diploma.data.entity;

import com.github.arlekinside.diploma.data.SecurityRoles;
import com.github.arlekinside.diploma.data.entity.finance.MoneyData;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUser")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
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
    private String password;

    @Embedded
    private TimeData timeData;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SecurityRoles role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Savings> savings;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Savings> savingsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @Embedded
    private MoneyData moneyData = new MoneyData();
}
