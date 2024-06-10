package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "items")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int price;

    @Column
    private int amount;

    @Column
    private int total;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;
}
