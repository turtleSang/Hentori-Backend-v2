package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
@Table(name = "receivable")
public class ReceivableEntity {

    @Id
    @Column(name = "order_id")
    private String id;

    @Column
    private int amount;

    @Column
    private int payment;

    @Column
    private int remaining;

    @Column
    private boolean status;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @OneToMany(mappedBy = "receivableEntity")
    private Set<RevenueEntity> revenueEntity;

}
