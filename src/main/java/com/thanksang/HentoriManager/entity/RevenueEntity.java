package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "revenue")
public class RevenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int amount;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;

    @ManyToOne
    @JoinColumn(name = "receivable_id")
    private ReceivableEntity receivableEntity;

}
