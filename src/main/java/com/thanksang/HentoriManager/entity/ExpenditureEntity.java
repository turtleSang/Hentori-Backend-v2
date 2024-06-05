package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "expenditure")
public class ExpenditureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int amount;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;


}
