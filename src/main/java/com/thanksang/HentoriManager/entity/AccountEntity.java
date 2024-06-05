package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
public class AccountEntity {
    @Id
    @Column(unique = true)
    private String name;

    @Column(name = "balance")
    private long balance;

    @OneToMany(mappedBy = "accountEntity")
    private Set<RevenueEntity> revenueEntitySet;

    @OneToMany(mappedBy = "accountEntity")
    private Set<ExpenditureEntity> expenditureEntitySet;
}
